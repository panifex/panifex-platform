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
package org.panifex.module.dashboard.impl;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.module.api.menu.AppMenuService;
import org.panifex.module.api.menu.OpenContentMenuAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = AppMenuServiceListener.ID)
@ReferenceListener
public class AppMenuServiceListener {

    public static final String ID = "org.panifex.module.dashboard.impl.AppMenuServiceListener";
    
    private Logger log = LoggerFactory.getLogger(AppMenuServiceListener.class);
    
    @Inject
    @Reference(
        availability = "optional",
        serviceInterface = AppMenuService.class,
        referenceListeners = @ReferenceListener(ref = ID))
    private AppMenuService service;
    
    private final OpenContentMenuAction action;
    
    public AppMenuServiceListener() {
        action = new OpenContentMenuAction(ID, "", DashboardContent.ID, 0);
        action.setLabel("Dashboard");
    }
    
    @Bind
    public void bindAppMenuService(AppMenuService service) {
        log.debug("Bind AppMenuService: {}", service);
        this.service = service;
        service.bindMenuItem(action);
    }
    
    @Unbind
    public void unbindAppMenuService(AppMenuService service) {
        log.debug("Unbind AppMenuService: {}", service);
        if (service != null) {
            service.unbindMenuItem(action);
        }
        this.service = null;
    }
}
