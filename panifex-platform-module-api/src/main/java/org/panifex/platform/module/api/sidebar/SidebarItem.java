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

/**
 * This is an abstract sidebar item which can be node or item. The sidebar items are used to define
 * dynamic sidebar menu.
 * 
 * @since 1.0
 */
public interface SidebarItem extends Comparable<SidebarItem> {

    /**
     * Returns the label
     * 
     * @return the label (never null)
     * @since 1.0
     */
    String getLabel();

    /**
     * Returns the icon font
     * 
     * @return the icon font
     * @since 1.0
     */
    String getIconSclass();

    /**
     * A priority of item. The items will be ordered by priority in sidebar menu.
     * 
     * @return the priority of item
     * @since 1.0
     */
    int getPriority();
}
