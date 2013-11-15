package org.panifex.platform.web.impl.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens to active Realm services and register or unregister its to the Shiro's security manager.
 * 
 */
@Bean(id = "org.panifex.platform.web.impl.security.SecurityRealmListener", dependsOn = "org.panifex.platform.web.impl.PaxWebServiceListener")
@ReferenceListener
public class SecurityRealmListener {

    private Logger log = LoggerFactory.getLogger(SecurityRealmListener.class);

    @Inject
    @ReferenceList(availability = "optional", serviceInterface = Realm.class, referenceListeners = @ReferenceListener(ref = "org.panifex.platform.web.impl.security.SecurityRealmListener"))
    private List<Realm> realms;

    @Inject(ref = "org.panifex.platform.web.impl.security.SecurityFilter")
    private SecurityFilter securityFilter;

    public void setSecurityFilter(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bind
    public void bind(Realm realm) {
        log.debug("Binding security realm: {}", realm);

        // add binded realm to security manager
        DefaultWebSecurityManager manager = getSecurityManager();
        Collection<Realm> realms = manager.getRealms();

        // if current realms list is null, create new one
        if (realms == null) {
            realms = new ArrayList<Realm>();
        }

        // add binded real to list
        realms.add(realm);
        manager.setRealms(realms);

    }

    @Unbind
    public void unbind(Realm realm) {
        log.debug("Unbinding security realm: {}", realm);

        if (realm != null) {
            // remove unbinded realm if it is not null
            DefaultWebSecurityManager manager = getSecurityManager();
            Collection<Realm> realms = manager.getRealms();
            if (realms != null) {
                realms.remove(realm);
                manager.setRealms(realms);
            }
        }
    }

    private DefaultWebSecurityManager getSecurityManager() {
        return (DefaultWebSecurityManager) securityFilter.getSecurityManager();
    }
}
