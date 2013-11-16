package org.panifex.platform.module.dashboard.impl;

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

    public final static String ID = "org.panifex.platform.module.dashboard.impl.DashboardSidebar";
    
    private Collection<SidebarItem> sidebarItems = new ArrayList<>();
    
    /**
     * Initializes Dashboard sidebar items;
     */
    public DashboardSidebar() {
        DefaultSidebarCommand dashboardItem = new DefaultSidebarCommand();
        dashboardItem.setLabel("Dashboard");
        sidebarItems.add(dashboardItem);
    }
    
    @Override
    public Collection<SidebarItem> getSidebarItems() {
        return sidebarItems;
    }

}
