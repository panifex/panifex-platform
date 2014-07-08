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
package org.panifex.web.shiro.env;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.panifex.service.api.security.SecurityService;
import org.panifex.web.shiro.mgt.ModularFilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ModularWebEnvironment that stores Shiro's objects that can be dynamically updated.
 */
public class ModularWebEnvironment extends IniWebEnvironment {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Internal collection of registered security services.
     */
    private Collection<Realm> securityServices = new ArrayList<>();

    /**
     * The filter chain manager which enables dynamically registering filters and their
     * mapping.
     */
    private ModularFilterChainManager filterChainManager;

    /**
     * Initializes this instance by calling {@link #configure() configure} for actual
     * instance configuration. This method overrides {@link super#init()} to avoid
     * processing INI instance or config locations.
     */
    @Override
    public void init() {
        configure();
    }

    /**
     * Binds a {@link SecurityService} to the Shiro's security manager.
     *
     * @param securityService the security service to be binded
     */
    public void bindSecurityService(SecurityService securityService) {
        log.debug("Bind security service: {}", securityService);
        securityServices.add(securityService);

        bindSecurityServices();
    }

    /**
     * Unbinds a {@link SecurityService} from the Shiro's security manager.
     *
     * @param securityService the security service to be unbinded
     */
    public void unbindSecurityService(SecurityService securityService) {
        log.debug("Unbind security service: {}", securityService);
        securityServices.remove(securityService);

        bindSecurityServices();
    }

    /**
     * Binds the registered security services to the realm security
     * manager.
     * <p>
     * If there is not registered services, an empty simple account realm
     * will be binded because it should be at least one binded realm.
     */
    protected final void bindSecurityServices() {
        RealmSecurityManager securityManager = (RealmSecurityManager)
                getSecurityManager();

        if (securityManager != null) {
            if (!securityServices.isEmpty()) {
                securityManager.setRealms(securityServices);
            } else {
                // bind simple realm because it should be at least one realm
                Collection<Realm> realms = new ArrayList<>();
                realms.add(new SimpleAccountRealm());
                securityManager.setRealms(realms);
            }
        } else {
            log.warn("Realm security manager is not available. Realms have not been updated");
        }
    }

    @Override
    protected FilterChainResolver createFilterChainResolver() {
        PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver)
                super.createFilterChainResolver();
        if (resolver == null) {
            Ini ini = getIni();
            IniFilterChainResolverFactory factory = new IniFilterChainResolverFactory(ini, objects);
            resolver = (PathMatchingFilterChainResolver) factory.getInstance();

            if (filterChainManager == null) {
                String msg = "filterChainManager must be initialized before creating FilterChainManager";
                log.error(msg);
                throw new IllegalStateException(msg);
            }
            resolver.setFilterChainManager(filterChainManager);
        }
        return resolver;
    }

    public void setFilterChainManager(ModularFilterChainManager filterChainManager) {
        this.filterChainManager = filterChainManager;
    }

    protected FilterChainManager getFilterChainManager() {
        return filterChainManager;
    }
}
