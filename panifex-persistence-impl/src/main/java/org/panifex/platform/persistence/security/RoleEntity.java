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

package org.panifex.platform.persistence.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.panifex.platform.api.security.Role;

@Entity
@StaticMetamodel(RoleEntity_.class)
@Table(name = "sec_role")
public class RoleEntity implements Role, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 8260877176766030641L;

    private Long id;
    
    private String name;
    
    private String description;
    
    private List<AccountEntity> accounts;
    
    private List<PermissionEntity> permissions;
    
    @Id
    @Column(name = "role_id", nullable = false)
    @Override
    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }

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

    @ManyToMany(mappedBy = "roles")
    protected List<AccountEntity> getAccounts() {
        return accounts;
    }
    
    protected void setAccounts(List<AccountEntity> accounts) {
        this.accounts = accounts;
    }
    
    @ManyToMany
    @JoinTable(name = "sec_role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    protected List<PermissionEntity> getPermissions() {
        return permissions;
    }
    
    protected void setPermissions(List<PermissionEntity> permissions) {
        this.setPermissions(permissions);
    }
    
}
