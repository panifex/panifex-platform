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
package org.panifex.platform.module.api.sidebar;

import java.util.List;

/**
 * This is a menu node. The node could contain children which could be other nodes or sidebar items.
 * 
 * @since 1.0
 */
public interface SidebarNode extends SidebarItem {

    /**
     * Returns a list of children. The children could be other nodes or sidebar items.
     * 
     * @return the list of sidebar menu items
     * @see SidebarCommand
     * @since 1.0
     */
    List<SidebarItem> getSidebarItems();

    /**
     * Returns the badge text of the {@link Nav}
     * 
     * @return the badge text of the nav.
     * @see Nav
     * @since 1.0
     */
    String getBadgeText();
}
