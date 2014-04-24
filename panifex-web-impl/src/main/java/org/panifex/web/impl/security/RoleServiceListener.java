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

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Destroy;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.service.api.security.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains a reference to the active {@link org.panifex.service.api.security.RoleService RoleService}
 * implementation.
 */
@Bean(id = RoleServiceListener.ID, factoryMethod = "init")
@ReferenceListener
public class RoleServiceListener {

    public static final String ID = "org.panifex.web.impl.security.RoleServiceListener";
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    /**
     * The singleton instance of {@link RoleServiceListener} class.
     */
    private static volatile RoleServiceListener manager;
    
    /**
     * The reference of the {@link org.panifex.service.api.security.RoleService RoleService}.
     * The reference is dynamically updated when the implementations of the {@link org.panifex.service.api.security.RoleService RoleService}
     * appear or disappear.
     */
    @Inject
    @Reference(
        serviceInterface = RoleService.class,
        referenceListeners = @ReferenceListener(ref = ID))
    private RoleService roleService;
    
    /**
     * Private construct which protects any other class from instantiating.
     */
    private RoleServiceListener() {
    }
    
    /**
     * Factory method which initializes {@link RoleServiceListener}.
     * 
     * @return the {@link RoleServiceListener} singleton instance
     */
    public static RoleServiceListener init() {
        if (manager == null) {
            synchronized(RoleServiceListener.class) {
                if (manager == null) {
                    manager = new RoleServiceListener();
                }
            }
        }
        return manager;
    }
    
    /**
     * Frees linked objects.
     */
    @Destroy
    public void destroy() {
        manager = null;
    }
    
    /**
     * Binds {@link org.panifex.service.api.security.RoleService RoleService} to the manager.
     * 
     * @param roleService the appeared {@link org.panifex.service.api.security.RoleService RoleService}
     */
    @Bind
    public void bind(RoleService roleService) {
        log.debug("Bind RoleService: {}", roleService);
        this.roleService = roleService;
    }
    
    /**
     * Unbinds {@link org.panifex.service.api.security.RoleService RoleService} from the manager
     * 
     * @param roleService the disappeared {@link org.panifex.service.api.security.RoleService RoleService}
     */
    @Unbind
    public void unbind(RoleService roleService) {
        log.debug("Unbind RoleService: {}", roleService);
        this.roleService = null;
    }
    
    /**
     * Returns the {@link org.panifex.service.api.security.RoleService RoleService},
     * or null if it does not exist.
     * 
     * @return the {@link org.panifex.service.api.security.RoleService RoleService}, or null if it does not exist
     */
    public static RoleService getService() {
        return manager.roleService;
    }
}
