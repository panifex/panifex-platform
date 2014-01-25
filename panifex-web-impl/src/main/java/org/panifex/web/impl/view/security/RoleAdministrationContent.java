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

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.panifex.module.api.content.AbstractContent;
import org.panifex.module.api.settings.SettingsContent;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

/**
 * The SettingsContent for administration of users' roles.
 */
@Bean(id = RoleAdministrationContent.ID)
@Service(interfaces = SettingsContent.class)
public final class RoleAdministrationContent extends AbstractContent implements SettingsContent {

    public static final String ID = "org.panifex.web.impl.view.security.RoleSettingsContent";
    
    /**
     * Contains the {@link java.lang.String String} constant in which is the view-model binded to the components.
     */
    protected static final String VM_BIND_ID = "vm";
    
    public RoleAdministrationContent() {
        super("Roles");
    }

    @Override
    public void createBody(Component parent) {
        Div body = new Div();
        parent.appendChild(body);
        
        // initialize Binder
        DefaultBinder binder = new DefaultBinder();
        binder.init(body, getViewModel(), null);
        body.setAttribute(VM_BIND_ID, binder.getViewModel());
        
        createRolesGrid(binder, body);
        
        binder.loadComponent(body, true);
    }
    
    private RoleAdministrationVM getViewModel() {
        return new RoleAdministrationVM();
    }
    
    private void createRolesGrid(DefaultBinder binder, Component parent) {
        Listbox listbox = new Listbox();
        parent.appendChild(listbox);
        
        Listhead listhead = new Listhead();
        listbox.appendChild(listhead);
        
        Listheader nameColumn = new Listheader("Name");
        listhead.appendChild(nameColumn);
        
        Listheader descColumn = new Listheader("Description");
        listhead.appendChild(descColumn);

        binder.addPropertyLoadBindings(listbox, "model", "vm.roles", null, null, null, null, null);
        listbox.setTemplate("model", new RoleListboxTemplate(binder));
    }
}
