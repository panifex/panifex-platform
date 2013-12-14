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
package org.panifex.module.api.environment;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.apache.aries.blueprint.annotation.Destroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = EnvironmentManager.ID, factoryMethod = "init")
@ReferenceListener
public final class EnvironmentManager {

    public final static String ID = "org.panifex.platform.module.api.environment.EnvironmentManager";
    
    private static Logger log = LoggerFactory.getLogger(EnvironmentManager.class);
    
    private EnvironmentManager() {} // protect from instanting - singleton

    private static EnvironmentManager manager;
    
    public static EnvironmentManager init() {
        log.debug("Initialize environment manager");
        if (manager == null) {
            manager = new EnvironmentManager();
            log.debug("Environment manager has been initialized");
        } else {
            log.warn("Environment manager has already been initialized");
        }
        return manager;
    }
    
    @Destroy
    public void destroy() {
        if (manager != null) {
            manager = null;
            log.debug("Environment manager has been destroyed");
        } else {
            log.debug("Environment manager has already been destroyed");
        }
    }
    
    @Inject
    @Reference(
            availability = "optional", 
            serviceInterface = EnvironmentService.class, 
            referenceListeners = @ReferenceListener(ref = ID))
    private EnvironmentService environmentService;
    
    @Bind
    public void bind(EnvironmentService environmentService) {
        log.debug("Bind environment service: {}", environmentService);
        this.environmentService = environmentService;
    }
    
    @Unbind
    public void unbind(EnvironmentService environmentService) {
        log.debug("Unbind environment service: {}", environmentService);
        this.environmentService = null;
    }
    
    public static EnvironmentService getService() {
        return manager.environmentService;
    }
}
