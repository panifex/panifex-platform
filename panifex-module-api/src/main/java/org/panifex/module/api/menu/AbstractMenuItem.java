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

import org.apache.shiro.SecurityUtils;

/**
 * An abstract implementation of the {@link MenuItem} interface.
 * <p>
 * It contains common elements of the menu's item, such as the item's label etc.
 * 
 * @since 1.0
 */
public abstract class AbstractMenuItem implements MenuItem {

    private static final String DEFAULT_WILDCARD_PERMISSION_EXPRESSION = "user";
    
    /**
     * The item's ID.
     */
    private final String id;
    
    /**
     * The item's parent {@link MenuNode node}'s ID. It must be set if the item 
     * is child of any {@link MenuNode}.
     */
    private final String parentId;
    
    /**
     * The item's label.
     */
    private String label;
    
    /**
     * The item's assigned <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">
     * Cascading Style Sheets class</a>.
     */
    private String iconSclass;
    
    /**
     * The item's priority which defines how the item is positioned over
     * other items in the same hierarchy level.
     */
    private int priority;
    
    /**
     * The item's <a href="http://shiro.apache.org/permissions.html">
     * wildcard permission expression</a>. It defines permissions which 
     * the user has have to view the item.
     */
    private String wildcardPermissionExpression;
    
    /**
     * Constructs the {@link AbstractMenuItem} with the specified item's ID.
     * 
     * @param id the {@link MenuItem}'s ID
     * 
     * @since 1.0
     */
    protected AbstractMenuItem(String id) {
        this(id, "");
    }
    
    /**
     * Constructs the {@link AbstractMenuItem} with the specified item's ID
     * and the specified priority defines the position in the same hierarchy level.
     * 
     * @param id the {@link MenuItem}'s ID
     * @param priority the priority which defines the position in the same hierarchy level
     * 
     * @since 1.0
     */
    protected AbstractMenuItem(String id, int priority) {
        this(id, "", priority);
    }
    
    /**
     * Constructs the {@link AbstractMenuItem} with the specified item's ID
     * and the specified parent {@link MenuNode}'s ID.
     * 
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode}'s ID
     * 
     * @since 1.0
     */
    protected AbstractMenuItem(String id, String parentId) {
        this(id, parentId, Integer.MAX_VALUE);
    }
    
    /**
     * Constructs the {@link AbstractMenuItem} with the specified item's ID,
     * the specified parent {@link MenuNode}'s ID and the priority which 
     * defines the position in the same hierarchy level.
     * 
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode node}'s ID
     * @param priority the priority which defines the position in the same hierarchy level
     * 
     * @since 1.0
     */
    protected AbstractMenuItem(
            String id, 
            String parentId, 
            int priority) {
        this(id, parentId, priority, DEFAULT_WILDCARD_PERMISSION_EXPRESSION);
    }
    
    /**
     * Constructs the {@link AbstractMenuItem} with the specified item's ID,
     * the specified parent {@link MenuNode}'s ID, the priority which defines the position
     * in the same hierarchy level and the defined <a href="http://shiro.apache.org/permissions.html">
     * wildcard permission expression</a> which defines permissions which the user has 
     * have to view the item.
     * 
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode node}'s ID
     * @param priority the priority which defines the position in the same hierarchy level
     * @param wildcardPermissionExpression the <a href="http://shiro.apache.org/permissions.html">wildcard permission expression</a>.
     * 
     * @since 1.0
     */
    protected AbstractMenuItem(
            String id, 
            String parentId, 
            int priority,
            String wildcardPermissionExpression) {
        this.id = id;
        this.parentId = (parentId != null) ? parentId : "";
        this.priority = priority;
        if (wildcardPermissionExpression != null) {
            this.wildcardPermissionExpression = wildcardPermissionExpression;
        } else {
            this.wildcardPermissionExpression = DEFAULT_WILDCARD_PERMISSION_EXPRESSION;
        }
    }
    
    /**
     * Returns the {@link MenuItem item}'s ID.
     * 
     * @return the {@link MenuItem item}'s ID.
     * 
     * @since 1.0
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Returns the parent {@link MenuNode node}'s ID, or null
     * if the item is placed to the root level.
     * 
     * @return the parent {@link MenuNode node}'s ID.
     * 
     * @since 1.0
     */
    @Override
    public String getParentId() {
        return parentId;
    }

    /**
     * Returns the item's label.
     * 
     * @return the item's label
     * 
     * @since 1.0
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * Sets the item's label.
     * 
     * @param label the item's label
     * 
     * @since 1.0
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * Returns the assigned item's assigned <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">
     * Cascading Style Sheets class</a>.
     * 
     * @return the assigned <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">Cascading Style Sheets class</a>
     * @since 1.0
     */
    @Override
    public String getIconSclass() {
        return iconSclass;
    }
    
    /**
     * Sets the assigned <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">
     * Cascading Style Sheets class</a>.
     * 
     * @param iconSclass the <a href="http://en.wikipedia.org/wiki/Cascading_Style_Sheets">Cascading Style Sheets class</a>
     * 
     * @since 1.0
     */
    public void setIconSclass(String iconSclass) {
        this.iconSclass = iconSclass;
    }

    /**
     * Returns the item's priority which defines how the item is positioned over
     * other items in the same hierarchy level
     * 
     * @return the position which defines how to item is positioned over other items
     * 
     * @since 1.0
     */
    @Override
    public int getPriority() {
        return priority;
    }
    
    /**
     * Sets the item's priority which defines how the item is positioned over
     * other items in the same hierarchy level in the menu tree.
     * 
     * @param priority the position which defines how to item is positioned over other items
     * 
     * @since 1.0
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

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
    @Override
    public String getType() {
        if (this instanceof MenuAction) {
            return ACTION;
        } else if (this instanceof MenuNode) {
            return NODE;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Returns the {@link org.panifex.module.api.content.Content Content}'s 
     * bookmark.
     * <p>
     * This is only used in {@link OpenContentMenuAction} which sets the 
     * bookmark and then the layout manager shows the defined 
     * {@link org.panifex.module.api.content.Content Content}.
     * <p>
     * Other classes should return the empty string.
     * 
     * @return the {@link org.panifex.module.api.content.Content Content}'s bookmark, or the empty string
     * 
     * @since 1.0
     */
    @Override
    public String getBookmark() {
        return "";
    }
    
    /**
     * Sets the <a href="http://shiro.apache.org/permissions.html">wildcard permission expression</a>
     * which defines permissions which the user has have to view the item.
     * 
     * @param wildcardPermissionExpression the <a href="http://shiro.apache.org/permissions.html">wildcard permission expression</a>
     * 
     * @since 1.0
     */
    public void setWildcardPermissionExpression(String wildcardPermissionExpression) {
        this.wildcardPermissionExpression = wildcardPermissionExpression;
    }

    /**
     * Returns true if the current user is permitted to view the {@link MenuItem item},
     * or false if it is not.
     * 
     * @return true if the current user is permitted to view the {@link MenuItem item}, or false if it is not
     * 
     * @since 1.0
     */
    @Override
    public boolean getIsPermitted() {
        boolean isPermitted = SecurityUtils.getSubject().isPermitted(wildcardPermissionExpression);
        return isPermitted;
    }
    
}
