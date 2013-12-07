/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.web.impl.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.panifex.web.impl.PaxWebServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens to active Realm services and register or unregister its to the Shiro's security manager.
 * 
 */
@Bean(id = SecurityRealmListener.ID, dependsOn = PaxWebServiceListener.ID)
@ReferenceListener
public class SecurityRealmListener {

    private Logger log = LoggerFactory.getLogger(SecurityRealmListener.class);

    public final static String ID = "org.panifex.web.impl.security.SecurityRealmListener";
    
    @Inject
    @ReferenceList(availability = "optional", serviceInterface = Realm.class, referenceListeners = @ReferenceListener(ref = ID))
    private Set<Realm> realms = new HashSet<>();

    @Inject
    @Reference(availability = "optional", serviceInterface = SecurityFilter.class, referenceListeners = @ReferenceListener(ref = ID))
    private SecurityFilter securityFilter;

    @Bind
    public void bind(SecurityFilter securityFilter) {
        log.debug("Bind shiro filter: {}", securityFilter);
        this.securityFilter = securityFilter;
        updateRealms();
    }
    
    @Unbind
    public void unbind(SecurityFilter securityFilter) {
        log.debug("Unbind shiro filter: {}", securityFilter);
        this.securityFilter = null;
    }

    @Bind
    public void bind(Realm realm) {
        log.debug("Binding security realm: {}", realm);

        realms.add(realm);
        
        updateRealms();
    }

    @Unbind
    public void unbind(Realm realm) {
        log.debug("Unbinding security realm: {}", realm);

        realms.remove(realm);
        updateRealms();
    }

    private DefaultWebSecurityManager getSecurityManager() {
        if (securityFilter != null) {
            return (DefaultWebSecurityManager) securityFilter.getSecurityManager();
        } else {
            return null;
        }
    }
    
    private void updateRealms() {
        DefaultWebSecurityManager manager = getSecurityManager();
        if (manager != null) {
            manager.setRealms(realms);
            log.debug("Realms has been updated");
        } else {
            log.debug("Unable to update realms. Security manager has not been registered");
        }
    }
}
