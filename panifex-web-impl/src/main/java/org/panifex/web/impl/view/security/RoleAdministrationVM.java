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

import java.util.List;

import org.panifex.service.api.security.RoleImpl;
import org.panifex.service.api.security.RoleService;
import org.panifex.web.impl.security.RoleServiceListener;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 * The view-model for users' roles administration.
 */
public final class RoleAdministrationVM {

    // attributes
    public static final String ROLES_ATTR = "roles";
    
    /**
     * Returns the collection of {@link RoleImpl} from the active {@link RoleService}.
     * 
     * @return the collection of {@link RoleImpl} from the active {@link RoleService}
     */
    public ListModel<RoleImpl> getRoles() {
        // get an active RoleService
        RoleService roleService = RoleServiceListener.getService();
        
        if (roleService != null) {
            // get roles from the service
            List<RoleImpl> roles = roleService.getRoles();
            
            return new ListModelList<RoleImpl>(roles);
        } else {
            return null;
        }
    }
    
}
