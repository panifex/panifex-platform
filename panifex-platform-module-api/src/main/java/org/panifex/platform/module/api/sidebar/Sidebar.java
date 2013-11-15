package org.panifex.platform.module.api.sidebar;

import java.util.List;

/**
 * A container of sidebar menu's items which will be merged to sidebar menu instance. This interface
 * should be registered as OSGi service. Panifex platform will dynamic register to the service and
 * merge to menu instance.
 * 
 * @since 1.0
 */
public interface Sidebar {

    /**
     * Returns a list of menu items. The list could contain other nodes or sidebar's items.
     * 
     * @return the list of menu items
     * @see SidebarItem
     * @see SidebarNode
     * @since 1.0
     */
    List<AbstractSidebarItem> getSidebarItems();
}
