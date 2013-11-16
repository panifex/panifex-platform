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
package org.panifex.platform.web.impl.sidebar;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zkmax.zul.Navitem;

public class SidebarTemplate implements Template {

    private Binder binder;

    public SidebarTemplate(Binder binder) {
        this.binder = binder;
    }

    @Override
    public Component[] create(Component parent, Component insertBefore, VariableResolver resolver,
            Composer composer) {

        final Navitem navItem = new Navitem();
        binder.addPropertyLoadBindings(navItem, "label", "item.label", null, null, null, null, null);
        binder.addPropertyLoadBindings(navItem, "iconSclass", "item.iconSclass", null, null, null,
                null, null);

        // append to the parent
        navItem.setParent(parent);

        Component[] components = new Component[1];
        components[0] = navItem;

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
