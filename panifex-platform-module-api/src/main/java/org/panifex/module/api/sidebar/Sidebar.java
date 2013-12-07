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
package org.panifex.module.api.sidebar;

import java.util.Collection;

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
     * @see SidebarCommand
     * @see SidebarNode
     * @since 1.0
     */
    Collection<SidebarItem> getSidebarItems();
}
