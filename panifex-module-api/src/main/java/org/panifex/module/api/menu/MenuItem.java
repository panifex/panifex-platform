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

    /**
     * The constant which can be used for binding the ID property
     * to the GUI components.
     * 
     * @see {@link MenuItem#getId()}
     */
    public static final String ID_PROPERTY = "id";
    
    /**
     * The constant which can be used for binding the parent {@link MenuNode}'s
     * ID property to the GUI components.
     * 
     * @see {@link MenuItem#getParentId()}
     */
    public static final String PARENT_ID_PROPERTY = "parentId";
    
    /**
     * The constant which can be used for binding the label property
     * to the GUI components.
     * 
     * @see {@link MenuItem#getLabel()}
     */
    public static final String LABEL_PROPERTY = "label";
    
    /**
     * The constant which can be used for binding the assigned 
     * <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">Cascading Style Sheets class</a> 
     * property to the GUI components.
     * 
     * @see {@link MenuItem#getIconSclass()}
     */
    public static final String ICON_S_CLASS_PROPERTY = "iconSclass";
    
    /**
     * The constant which can be used for binding the priority property
     * to the GUI components.
     * 
     * @see {@link MenuItem#getPriority()}
     */
    public static final String PRIORITY_PROPERTY = "priority";
    
    /**
     * The constant which can be used for binding the type property
     * to the GUI components.
     * 
     * @see {@link MenuItem#getType()}
     */
    public static final String TYPE_PROPERTY = "type";
    
    /**
     * The constant which can be used for binding the bookmark property
     * to the GUI components.
     * 
     * @see {@link MenuItem#getBookmark()}
     */
    public static final String BOOKMARK_PROPERTY = "bookmark";
    
    /**
     * The constant which can be used for binding the isPermitted
     * property to the GUI components.
     * 
     * @see {@link MenuItem#getIsPermitted()}
     */
    public static final String IS_PERMITTED_PROPERTY = "isPermitted";
    
    /**
     * The {@link MenuItem#ACTION} constant defines that the {@link MenuItem}
     * is type of {@link MenuAction}.
     * 
     * @see {@link MenuItem#getType()}
     */
    public static final String ACTION = "action";
    
    /**
     * The {@link MenuItem#NODE} constant defines that the {@link MenuItem}
     * is type of {@link MenuNode}.
     * 
     * @see {@link MenuItem#getType()}
     */
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
     * Returns the assigned item's assigned <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">
     * Cascading Style Sheets class</a>.
     * 
     * @return the assigned <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">Cascading Style Sheets class</a>
     * 
     * @since 1.0
     */
    String getIconSclass();

    /**
     * Returns the item's priority which defines how the item is positioned over
     * other items in the same hierarchy level
     * 
     * @return the position which defines how to item is positioned over other items
     * 
     * @since 1.0
     */
    int getPriority();
    
    /**
     * Returns the concrete type of {@link MenuItem item}.
     * <p>
     * It returns {@link MenuItem#ACTION} if the item is type of {@link MenuAction},
     * or it returns {@link MenuItem#NODE} if the item is type of {@link MenuNode}.
     * 
     * @return the concrete type of {@link MenuItem item}
     * 
     * @see {@link org.panifex.web.impl.menu.MenuNodeTemplate}
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
