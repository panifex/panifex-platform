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
package org.panifex.web.impl.menu;

import org.panifex.module.api.menu.MenuItem;
import org.zkoss.bind.sys.TemplateResolver;

/**
 * A constants container for wiring {@link org.panifex.module.api.menu.MenuItem}'s
 * to the Web application menu.
 */
public final class AppMenuConstants {

    /**
     * Private construct which protects other class from instancing -
     * singleton.
     */
    private AppMenuConstants() {
    }
    
    /**
     * The prefix constant for binding properties.
     */
    public static final String ITEM_BIND_ID = TemplateResolver.EACH_VAR + ".data";
    
    /**
     * The arguments for the command binding.
     * 
     * @see {@link MenuActionTemplate#create(org.zkoss.zk.ui.Component, org.zkoss.zk.ui.Component, org.zkoss.xel.VariableResolver, org.zkoss.zk.ui.util.Composer)
     * MenuActionTemplate#create(...)}
     */
    public static final String[] ITEM_BIND_ARGS = new String[]{ ITEM_BIND_ID };
    
    /**
     * The constant which contains the condition which decides which 
     * {@link org.zkoss.zk.ui.util.Template Template} will be used.
     * <p>
     * The condition is based on {@link org.panifex.module.api.menu.MenuItem#getType() MenuItem#getType()}
     * property.
     */
    public static final String NODE_CHILDREN_CONDITION = TemplateResolver.EACH_VAR + ".data." + MenuItem.TYPE_PROPERTY;
    
    /**
     * The constant for binding the bookmark property to the GUI components.
     */
    public static final String BOOKMARK_PROPERTY = ITEM_BIND_ID + "." + MenuItem.BOOKMARK_PROPERTY;
    
    /**
     * The constant for binding the label property to the GUI components.
     */
    public static final String LABEL_PROPERTY = ITEM_BIND_ID + "." + MenuItem.LABEL_PROPERTY;
    
    /**
     * The constant for binding the assigned Cascading Style Sheets class 
     * property to the GUI components.
     */
    public static final String ICON_S_CLASS_PROPERTY = ITEM_BIND_ID + "." + MenuItem.ICON_S_CLASS_PROPERTY;
    
    /**
     * The constant for binding the disabled property.
     * <p>
     * It is based on {@link org.panifex.module.api.menu.MenuItem#getIsPermitted() MenuItem#getIsPermitted()}
     * property.
     */
    public static final String DISABLED_PROPERTY = "not " + ITEM_BIND_ID + "." + MenuItem.IS_PERMITTED_PROPERTY;
    
    /**
     * The constant for binding the visible property.
     * <p>
     * It is based on {@link org.panifex.module.api.menu.MenuItem#getIsPermitted() MenuItem#getIsPermitted()}
     * property.
     */
    public static final String VISIBLE_PROPERTY = ITEM_BIND_ID + "." + MenuItem.IS_PERMITTED_PROPERTY;
}
