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
package org.panifex.persistence.security;

import org.panifex.service.api.security.RoleImpl;

/**
 * A RoleImplBuilder builds the {@link org.panifex.service.api.security.RoleImpl RoleImpl} 
 * instance based on the existed {@link RoleEntity}.
 */
public final class RoleImplBuilder {

    private Long id;
    private int optlockVersion;
    private String name;
    private String description;
    
    public RoleImplBuilder(RoleEntity roleEntity) {
        id = roleEntity.getId();
        optlockVersion = roleEntity.getOptlockVersion();
        name = roleEntity.getName();
        description = roleEntity.getDescription();
    }
    
    public RoleImpl build() {
        RoleImpl roleImpl = new RoleImpl(
            id,
            optlockVersion,
            name,
            description);
        
        return roleImpl;
    }
}
