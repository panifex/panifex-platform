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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

@Entity
@IdClass(RolePermissionAssociationId.class)
@StaticMetamodel(RolePermissionAssociationEntity_.class)
@Table(name = "sec_role_permission")
public class RolePermissionAssociationEntity {

    private int roleId;
    private int permissionId;
    
    private RoleEntity role;
    private PermissionEntity permission;
    
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
}
