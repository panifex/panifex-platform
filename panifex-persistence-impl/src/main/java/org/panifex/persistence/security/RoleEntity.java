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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.panifex.persistence.AbstractEntity;
import org.panifex.service.api.security.Role;

@Entity
@StaticMetamodel(RoleEntity_.class)
@Table(name = "sec_role")
public class RoleEntity extends AbstractEntity implements Role, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 8260877176766030641L;
    
    private String name;
    
    private String description;
    
    private List<AccountRoleAssociationEntity> accountRoleAssociations;
    
    private List<PermissionEntity> permissions;
    
    @Column(name = "name", nullable = false, unique = true)
    @Override
    public String getName() {
        return name;
    }
    
    protected void setName(String name) {
        this.name = name;
    }
   
    @Column(name = "description", nullable = false)
    @Override
    public String getDescription() {
        return description;
    }
    
    protected void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "role")
    protected List<AccountRoleAssociationEntity> getAccountRoleAssociations() {
        return accountRoleAssociations;
    }
    
    protected void setAccountRoleAssociations(List<AccountRoleAssociationEntity> accountRoleAssociations) {
        this.accountRoleAssociations = accountRoleAssociations;
    }
    
    @ManyToMany
    @JoinTable(name = "sec_role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    protected List<PermissionEntity> getPermissions() {
        return permissions;
    }
    
    protected void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
    
}
