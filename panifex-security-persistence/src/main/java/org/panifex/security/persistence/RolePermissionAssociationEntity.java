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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@IdClass(RolePermissionAssociationId.class)
@StaticMetamodel(RolePermissionAssociationEntity_.class)
@Table(name = "sec_role_permission")
public class RolePermissionAssociationEntity {

    private int roleId;
    private int permissionId;
    
    private RoleEntity role;
    private PermissionEntity permission;
    
    protected RolePermissionAssociationEntity() {
    }
    
    @Id
    @Column(name = "role_id")
    protected int getRoleId() {
        return roleId;
    }
    
    protected void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    @Id
    @Column(name = "permission_id")
    protected int getPermissionId() {
        return permissionId;
    }
    
    protected void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
    
    @ManyToOne
    public RoleEntity getRole() {
        return role;
    }
    
    protected void setRole(RoleEntity role) {
        this.role = role;
    }
    
    @ManyToOne
    protected PermissionEntity getPermission() {
        return permission;
    }
    
    protected void setPermission(PermissionEntity permission) {
        this.permission = permission;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                append(roleId).
                append(permissionId).
                append(role).
                append(permission).
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
        RolePermissionAssociationEntity other = (RolePermissionAssociationEntity) obj;
        return new EqualsBuilder().
                append(roleId, other.roleId).
                append(permissionId, other.permissionId).
                append(role, other.role).
                append(permission, other.permission).
                isEquals();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("roleId", roleId).
                append("permissionId", permissionId).
                append("role", role).
                append("permission", permission).
                toString();
    }
}
