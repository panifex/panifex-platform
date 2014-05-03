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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.panifex.persistence.spi.AbstractEntity;
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
    
    private List<RolePermissionAssociationEntity> rolePermissionAssociations;
    
    protected RoleEntity() {
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

    @OneToMany(mappedBy = "role")
    protected List<AccountRoleAssociationEntity> getAccountRoleAssociations() {
        return accountRoleAssociations;
    }
    
    protected void setAccountRoleAssociations(List<AccountRoleAssociationEntity> accountRoleAssociations) {
        this.accountRoleAssociations = accountRoleAssociations;
    }
    
    @OneToMany(mappedBy = "role")
    protected List<RolePermissionAssociationEntity> getRolePermissionAssociations() {
        return rolePermissionAssociations;
    }
    
    protected void setRolePermissionAssociations(List<RolePermissionAssociationEntity> rolePermissionAssociations) {
        this.rolePermissionAssociations = rolePermissionAssociations;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                appendSuper(super.hashCode()).
                append(name).
                append(description).
                append(accountRoleAssociations).
                append(rolePermissionAssociations).
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
        RoleEntity other = (RoleEntity) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(name, other.name).
                append(description, other.description).
                append(accountRoleAssociations, other.accountRoleAssociations).
                append(rolePermissionAssociations, other.rolePermissionAssociations).
                isEquals();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("name", name).
                append("description", description).
                append("accountRoleAssociations", accountRoleAssociations).
                append("rolePermissionAssociations", rolePermissionAssociations).
                toString();
    }
}
