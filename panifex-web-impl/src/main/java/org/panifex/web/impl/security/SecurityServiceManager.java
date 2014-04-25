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

import org.panifex.service.api.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecurityServiceManager {

    public static final String ID = "org.panifex.web.impl.security.SecurityServiceManager";
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    /**
     * The singleton instance of {@link SecurityServiceManager} class.
     */
    private static volatile SecurityServiceManager manager;

    /**
     * The reference of the {@link org.panifex.service.api.security.SecurityService SecurityService}.
     * The reference is dynamically updated when the implementations of the {@link org.panifex.service.api.security.SecurityService SecurityService}
     * appear or disappear.
     */
    private SecurityService securityService;
    
    /**
     * Private construct which protects any other class from instantiating.
     */
    private SecurityServiceManager() {
    }
    
    /**
     * Factory method which initializes {@link SecurityServiceManager}.
     * 
     * @return the {@link SecurityServiceManager} singleton instance
     */
    public static SecurityServiceManager init() {
        if (manager == null) {
            synchronized(SecurityServiceManager.class) {
                if (manager == null) {
                    manager = new SecurityServiceManager();
                }
            }
        }
        return manager;
    }
    
    /**
     * Frees linked objects.
     */
    public void destroy() {
        manager = null;
    }
    
    /**
     * Binds {@link org.panifex.service.api.security.SecurityService SecurityService} to the manager.
     * 
     * @param securityService the appeared {@link org.panifex.service.api.security.SecurityService SecurityService}
     */
    public void bind(SecurityService securityService) {
        log.debug("Bind SecurityService: {}", securityService);
        this.securityService = securityService;
    }
    
    /**
     * Unbinds {@link org.panifex.service.api.security.SecurityService SecurityService} from the manager
     * 
     * @param securityService the disappeared {@link org.panifex.service.api.security.SecurityService SecurityService}
     */
    public void unbind(SecurityService securityService) {
        log.debug("Unbind SecurityService: {}", securityService);
        this.securityService = null;
    }
    
    /**
     * Returns the {@link org.panifex.service.api.security.SecurityService SecurityService},
     * or null if it does not exist.
     * 
     * @return the {@link org.panifex.service.api.security.SecurityService SecurityService}, or null if it does not exist
     */
    public static SecurityService getService() {
        return manager.securityService;
    }
}
