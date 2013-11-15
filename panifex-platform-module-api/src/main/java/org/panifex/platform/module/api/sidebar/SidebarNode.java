package org.panifex.platform.module.api.sidebar;

import java.util.List;

/**
 * This is a menu node. The node could contain children which could be other nodes or sidebar items.
 * 
 * @since 1.0
 */
public interface SidebarNode extends AbstractSidebarItem {

    /**
     * Returns a list of children. The children could be other nodes or sidebar items.
     * 
     * @return the list of sidebar menu items
     * @see SidebarItem
     * @since 1.0
     */
    List<AbstractSidebarItem> getSidebarItems();

    /**
     * Returns the badge text of the {@link Nav}
     * 
     * @return the badge text of the nav.
     * @see Nav
     * @since 1.0
     */
    String getBadgeText();
}
