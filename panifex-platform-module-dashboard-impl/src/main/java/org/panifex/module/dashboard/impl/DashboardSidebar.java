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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.panifex.platform.module.api.sidebar.Sidebar;
import org.panifex.platform.module.api.sidebar.DefaultSidebarCommand;
import org.panifex.platform.module.api.sidebar.SidebarItem;

@Bean(id = DashboardSidebar.ID)
@Service(interfaces = Sidebar.class)
public class DashboardSidebar implements Sidebar {

    public final static String ID = "org.panifex.module.dashboard.impl.DashboardSidebar";
    
    private Collection<SidebarItem> sidebarItems = new ArrayList<>();
    
    /**
     * Initializes Dashboard sidebar items;
     */
    public DashboardSidebar() {
        // create dashboard sidebar item
        DefaultSidebarCommand dashboardItem = new DefaultSidebarCommand(
            "Dashboard",
            DashboardContent.ID,
            0);
        
        dashboardItem.setIconSclass("glyphicon glyphicon-home");
        
        // add item to list
        sidebarItems.add(dashboardItem);
    }
    
    @Override
    public Collection<SidebarItem> getSidebarItems() {
        return sidebarItems;
    }

}
