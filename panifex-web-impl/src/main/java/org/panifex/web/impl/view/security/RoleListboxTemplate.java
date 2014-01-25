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
package org.panifex.web.impl.view.security;

import java.util.HashMap;
import java.util.Map;

import org.panifex.service.api.security.Role;
import org.zkoss.bind.Binder;
import org.zkoss.bind.sys.TemplateResolver;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 * A {@link org.zkoss.zk.ui.util.Template Template} for building
 * {@link org.zkoss.zul.Listitem Listitem} components based on the {@link org.panifex.module.api.menu.RoleImpl RoleImpl}.
 */
class RoleListboxTemplate implements Template {

    public static final String NAME_PROP = TemplateResolver.EACH_ATTR + "." + Role.NAME_PROP;
    public static final String DESCRIPTION_PROP = TemplateResolver.EACH_ATTR + "." + Role.DESCRIPTION_PROP;
    
    /**
     * The {@link org.zkoss.bind.Binder Binder} for manage data bindings.
     */
    private final Binder binder;

    private Map<String, Object> params;
    
    /**
     * Creates a new {@link RoleListboxTemplate} for creating
     * {@link org.zkoss.zul.Listcell Listcell} components.
     * 
     * @param binder the {@link org.zkoss.bind.Binder binder} for manage data bindings.
     */
    public RoleListboxTemplate(Binder binder) {
        this.binder = binder;
    }
    
    @Override
    public Component[] create(Component parent, Component insertBefore, VariableResolver resolver,
            @SuppressWarnings("rawtypes") Composer composer) {
        
        Listitem listitem = new Listitem();

        // Name
        Listcell nameCell = new Listcell();
        listitem.appendChild(nameCell);
        binder.addPropertyLoadBindings(nameCell, "label", NAME_PROP, null, null, null, null, null);
        
        // Description
        Listcell descCell = new Listcell();
        listitem.appendChild(descCell);
        binder.addPropertyLoadBindings(descCell, "label", DESCRIPTION_PROP, null, null, null, null, null);
        
        //append to the parent
        if (insertBefore ==null){
                parent.appendChild(listitem);
        }else{
                parent.insertBefore(listitem, insertBefore);
        }
        
        Component[] components = new Component[] { listitem };
        return components;
    }

    @Override
    public Map<String, Object> getParameters() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

}
