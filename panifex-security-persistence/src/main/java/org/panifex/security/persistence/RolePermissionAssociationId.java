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
package org.panifex.security.persistence;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RolePermissionAssociationId implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -7087663068350957205L;

    private int roleId;
    private int permissionId;
    
    public RolePermissionAssociationId() {
    }
    
    protected int getRoleId() {
        return roleId;
    }
    
    protected void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    protected int getPermissionId() {
        return permissionId;
    }
    
    protected void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(5, 67).
                append(roleId).
                append(permissionId).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        RolePermissionAssociationId other = (RolePermissionAssociationId) obj;
        return new EqualsBuilder().
                append(roleId, other.roleId).
                append(permissionId, other.permissionId).
                isEquals();
            
    }
}
