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

public abstract class AbstractMenuItem implements MenuItem {

    private static final String DEFAULT_WILDCARD_PERMISSION_EXPRESSION = "user";
    
    private final String id;
    private final String parentId;
    private String label;
    private String iconSclass;
    private int priority;
    private String wildcardPermissionExpression;
    
    protected AbstractMenuItem(String id) {
        this(id, "");
    }
    
    protected AbstractMenuItem(String id, int priority) {
        this(id, "", priority);
    }
    
    protected AbstractMenuItem(String id, String parentId) {
        this(id, parentId, Integer.MAX_VALUE);
    }
    
    protected AbstractMenuItem(
            String id, 
            String parentId, 
            int priority) {
        this(id, parentId, priority, DEFAULT_WILDCARD_PERMISSION_EXPRESSION);
    }
    
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
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    public String getIconSclass() {
        return iconSclass;
    }
    
    public void setIconSclass(String iconSclass) {
        this.iconSclass = iconSclass;
    }

    @Override
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }

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
     * Return content id. It is only used in OpenContentMenuAction. No one else 
     * should override this method.
     * 
     */
    @Override
    public String getContentId() {
        return "";
    }
    
    public void setWildcardPermissionExpression(String wildcardPermissionExpression) {
        this.wildcardPermissionExpression = wildcardPermissionExpression;
    }

    @Override
    public boolean getIsPermitted() {
        boolean isPermitted = SecurityUtils.getSubject().isPermitted(wildcardPermissionExpression);
        return isPermitted;
    }
    
}
