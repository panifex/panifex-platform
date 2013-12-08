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
package org.panifex.web.impl.menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Register;
import org.apache.aries.blueprint.annotation.RegistrationListener;
import org.apache.aries.blueprint.annotation.Unregister;
import org.panifex.module.api.menu.AppMenuService;
import org.panifex.module.api.menu.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.TreeNode;

@Bean(id = AppMenuManager.ID)
@RegistrationListener
public final class AppMenuManager implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1406210037353560969L;

    public static final String ID = "org.panifex.web.impl.menu.AppMenuManager";
    
    private Logger log = LoggerFactory.getLogger(AppMenuManager.class);
    
    private static AppMenuService service;
    
    @Register
    public void registerAppMenuService(Serializable service, Map<String, String> props) {
        log.debug("Bind AppMenuService: {}", service);
        AppMenuManager.service = (AppMenuService) service;
    }
    
    @Unregister
    public void unregisterAppMenuService(AppMenuService service, Map<String, String> props) {
        log.debug("Unbind AppMenuService: {}", service);
        AppMenuManager.service = (AppMenuService) service;
    }
    
    public static List<TreeNode<MenuItem>> getMenuItems() {
        return service.getMenuItems();
    }
}
