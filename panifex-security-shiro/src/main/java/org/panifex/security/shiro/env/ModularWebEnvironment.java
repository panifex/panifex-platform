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
package org.panifex.security.shiro.env;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.panifex.module.api.security.AuthenticationService;
import org.panifex.module.api.security.AuthorizationService;
import org.panifex.security.shiro.mgt.ModularFilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ModularWebEnvironment that stores Shiro's objects that can be dynamically updated.
 */
public class ModularWebEnvironment extends IniWebEnvironment {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * The filter chain manager which enables dynamically registering filters and their
     * mapping.
     */
    private final ModularFilterChainManager filterChainManager;

    /**
     * Internal collection of registered realms that will be synchronized to
     * the {@link RealmSecurityManager}.
     */
    private final Map<String, Realm> realms = new HashMap<>();

    private final Object LOCK = new Object();

    /**
     * Initializes a new ModularWebEnvironment object instance.
     *
     * @param filterChainManager
     *      the modular filter chain manager
     */
    public ModularWebEnvironment(ModularFilterChainManager filterChainManager) {
        if (filterChainManager == null) {
            throw new IllegalArgumentException("modularFilterChainManager cannot be null");
        }
        this.filterChainManager = filterChainManager;
    }

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
     * Binds the {@link AuthenticationService} to the security manager.
     *
     * @param authcService
     *      the {@link AuthenticationService} to be binded to the security manager
     */
    public void bindAuthenticationService(AuthenticationService authcService) {
        log.debug("Bind authentication service: {}", authcService);
        if (authcService != null) {
            bindRealm(authcService);
        }
    }

    /**
     * Unbinds the {@link AuthenticationService} from the security manager.
     *
     * @param authcService
     *      the {@link AuthenticationService} to be unbinded from the security manager
     */
    public void unbindAuthenticationService(AuthenticationService authcService) {
        log.debug("Unbind authentication service: {}", authcService);
        if (authcService != null) {
            unbindRealm(authcService, null);
        }
    }

    /**
     * Binds the {@link AuthorizationService} to the security manager
     *
     * @param authzService
     *      the {@link AuthorizationService} to be binded to the security manager
     */
    public void bindAuthorizationService(AuthorizationService authzService) {
        log.debug("Bind authorization service: {}", authzService);
        if (authzService != null) {
            bindRealm(new AuthzAwareRealm(authzService));
        }
    }

    /**
     * Unbinds the {@link AuthorizationService} from the security manager.
     *
     * @param authzService
     *      the {@link AuthorizationService} to be unbinded from the security manager
     */
    public void unbindAuthorizationService(AuthorizationService authzService) {
        log.debug("Unbind authorization service: {}", authzService);
        if (authzService != null) {
            unbindRealm(null, authzService);
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

            resolver.setFilterChainManager(filterChainManager);
        }
        return resolver;
    }

    protected FilterChainManager getFilterChainManager() {
        return filterChainManager;
    }

    /**
     * Asserts the provided service name is not null or a blank string.
     *
     * @param authcService
     *      the service name to be verified
     */
    protected void assertServiceNameValid(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("authenticationService's name must be specified");
        }
    }

    /**
     * Binds the {@link Realm} to the security manager.
     *
     * @param realm
     *      the {@link Realm} to be binded
     */
    protected void bindRealm(Realm realm) {
        synchronized (LOCK) {
            String serviceName = realm.getName();
            assertServiceNameValid(serviceName);
            if (!realms.containsKey(serviceName)) {
                realms.put(serviceName, realm);
                synchronizeRealms();
            } else {
                String msg = "There is already registered service with the same name: " +
                        serviceName;
                log.warn(msg);
            }
        }
    }

    /**
     * Unbinds the {@link AuthenticationService} or the {@link AuthorizationService} from
     * the security manager.
     * <p>
     * The method access either the authentication of the authorization service. It is
     * illegal to remove both at the single method call.
     *
     * @param authcService
     *      the {@link AuthenticationService} to be unbinded from the security manager
     * @param authzService
     *      the {@link AuthorizationService} to be unbinded from the security manager
     */
    protected void unbindRealm(AuthenticationService authcService, AuthorizationService authzService) {
        synchronized (LOCK) {
            String serviceName;
            if (authcService != null) {
                serviceName = authcService.getName();
            } else if (authzService != null) {
                serviceName = authzService.getName();
            } else {
                String msg = "Either authentication or authorization service should be provided";
                log.error(msg);
                throw new IllegalArgumentException(msg);
            }

            assertServiceNameValid(serviceName);

            Realm realm = realms.get(serviceName);

            if (realm != null) {
                if (authcService != null && realm.equals(authcService)) {
                    removeRealm(serviceName);
                    return;
                } else if (authzService != null && realm instanceof AuthzAwareRealm) {
                    AuthzAwareRealm authzAwareRealm = (AuthzAwareRealm) realm;
                    if (authzService.equals(authzAwareRealm.getAuthorizationService())) {
                        removeRealm(serviceName);
                        return;
                    }
                }
            }

            String msg = "Service with name " + serviceName + " is not found";
            log.warn(msg);
        }
    }

    /**
     * Removes the realm from the realm's map and synchronizes it with the security manager.
     *
     * @param serviceName
     *      the service's name to be removed
     */
    private void removeRealm(String serviceName) {
        realms.remove(serviceName);
        synchronizeRealms();
    }

    /**
     * Synchronizes the realm's map with the security manager.
     */
    protected final void synchronizeRealms() {
        RealmSecurityManager securityManager = (RealmSecurityManager)
                getSecurityManager();

        if (securityManager != null) {
            if (!realms.isEmpty()) {
                securityManager.setRealms(realms.values());
            } else {
                // bind simple realm because it should be at least one realm
                securityManager.setRealm(new SimpleAccountRealm());
            }
        } else {
            log.warn("Realm security manager is not available. Realms is not updated");
        }
    }

    /**
     * Gets the collection of currently registered {@link Realm}s.
     *
     * @return
     *      the collection of registered {@link Realm}s
     */
    protected Collection<Realm> getRealms() {
        return realms.values();
    }
}
