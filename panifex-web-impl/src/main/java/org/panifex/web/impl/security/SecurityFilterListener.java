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
import org.panifex.service.api.security.SecurityService;
import org.panifex.web.impl.WebContainerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens to active Realm services and register or unregister its to the Shiro's security manager.
 * 
 */
@Bean(id = SecurityFilterListener.ID, dependsOn = WebContainerListener.ID)
@ReferenceListener
public class SecurityFilterListener {

    private Logger log = LoggerFactory.getLogger(SecurityFilterListener.class);

    public final static String ID = "org.panifex.web.impl.security.SecurityFilterListener";

    @Inject
    @Reference(
        availability = "optional", 
        serviceInterface = SecurityFilter.class, 
        referenceListeners = @ReferenceListener(ref = ID))
    private SecurityFilter securityFilter;

    @Inject
    @ReferenceList(
        availability = "optional",
        serviceInterface = SecurityService.class,
        referenceListeners = @ReferenceListener(ref = ID))
    private Set<SecurityService> securityServices = new HashSet<>();
    
    
    @Bind
    public void bind(SecurityFilter securityFilter) {
        log.debug("Bind security filter: {}", securityFilter);
        this.securityFilter = securityFilter;
        updateRealms();
    }
    
    @Unbind
    public void unbind(SecurityFilter securityFilter) {
        log.debug("Unbind security filter: {}", securityFilter);
        this.securityFilter = null;
    }

    @Bind
    public void bind(SecurityService securityService) {
        log.debug("Bind security service: {}", securityService);
        securityServices.add(securityService);
    }
    
    @Unbind
    public void unbind(SecurityService securityService) {
        log.debug("Unbind security service: {}", securityService);
        securityServices.remove(securityService);
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
        if (manager != null && !securityServices.isEmpty()) {
            // TODO Check what to do when realms is empty
            
            Set<Realm> realms = new HashSet<>();
            realms.addAll(securityServices);
            
            manager.setRealms(realms);
            log.debug("Realms has been updated");
        } else {
            log.debug("Unable to update realms. Security manager has not been registered");
        }
    }
}
