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
package org.panifex.module.api.menu;

/**
 * A {@link MenuItem} defines a building block of the application menu.
 * <p>
 * The menu item could be either a {@link MenuAction} which defines a clickable
 * item, or a {@link MenuNode} which is a container of child items.
 *
 * @since 1.0
 */
public interface MenuItem {

    // constants which is used in a menu implementation
    public static final String ACTION = "action";
    public static final String NODE = "node";
    
    /**
     * Returns the item's ID.
     * 
     * @return the item's ID
     * 
     * @since 1.0
     */
    String getId();
    
    /**
     * Returns the parent node's ID.
     * 
     * @return the parent node's ID
     * 
     * @since 1.0
     */
    String getParentId();
    
    /**
     * Returns the label
     * 
     * @return the label (never null)
     * 
     * @since 1.0
     */
    String getLabel();

    /**
     * Returns the icon font
     * 
     * @return the icon font
     * 
     * @since 1.0
     */
    String getIconSclass();

    /**
     * A priority of item. The items will be ordered by priority in sidebar menu.
     * 
     * @return the priority of item
     * 
     * @since 1.0
     */
    int getPriority();
    
    /**
     * Returns item's type. Type can be 'action' or 'node.
     * 
     * <p>This method is used by ZK template resolver.
     * 
     * @return item's type
     * 
     * @since 1.0
     */
    String getType();
    
    /**
     * Returns the {@link org.panifex.module.api.content.Content Content}'s 
     * bookmark.
     * <p>
     * This is only used in {@link OpenContentMenuAction} which sets the 
     * bookmark and then the layout manager shows the defined 
     * {@link org.panifex.module.api.content.Content Content}.
     * 
     * @return the {@link org.panifex.module.api.content.Content Content}'s bookmark
     * 
     * @since 1.0
     */
    String getBookmark();
    
    /**
     * Returns {@code true} if the logged account has permission to open a node or 
     * to perform an action .
     * 
     * @return true if an account is permitted, false otherwise
     * 
     * @since 1.0
     */
    boolean getIsPermitted();
}
