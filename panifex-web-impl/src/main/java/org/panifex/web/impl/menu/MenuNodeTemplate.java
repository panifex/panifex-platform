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

import java.util.HashMap;
import java.util.Map;

import org.panifex.module.api.menu.MenuItem;
import org.zkoss.bind.Binder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zkmax.zul.Nav;

public final class MenuNodeTemplate implements Template {

    private Binder binder;
    
    public MenuNodeTemplate(Binder binder) {
        this.binder = binder;
    }
    
    @Override
    public Component[] create(Component parent, Component insertBefore, VariableResolver resolver,
            @SuppressWarnings("rawtypes") Composer composer) {
        
        final Nav node = new Nav();
        
        // property bindings
        binder.addPropertyLoadBindings(node, "label", AppMenuConstants.LABEL_PROPERTY, null, null, null, null, null);
        binder.addPropertyLoadBindings(node, "iconSclass", AppMenuConstants.ICON_S_CLASS_PROPERTY, null, null, null, null, null);
        binder.addPropertyLoadBindings(node, "disabled", AppMenuConstants.DISABLED_PROPERTY, null, null, null, null, null);
        binder.addPropertyLoadBindings(node, "visible", AppMenuConstants.VISIBLE_PROPERTY, null, null, null, null, null);
        
        // children binding
        binder.addChildrenLoadBindings(node, "item.children", null, null, null, null, null);
        binder.setTemplate(node, "$CHILDREN$", AppMenuConstants.NODE_CHILDREN_CONDITION, null);
        node.setTemplate(MenuItem.ACTION, new MenuActionTemplate(binder));
        node.setTemplate(MenuItem.NODE, new MenuNodeTemplate(binder));
        
        // append to the parent
        node.setParent(parent);

        Component[] components = new Component[1];
        components[0] = node;

        return components;
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        // set binding variable
        parameters.put("var", "item");

        return parameters;
    }

}
