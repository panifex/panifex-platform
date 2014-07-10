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
import org.panifex.module.api.accounts.Permission;
import org.panifex.persistence.spi.AbstractEntity;

@Entity
@StaticMetamodel(PermissionEntity_.class)
@Table(name = "sec_permission")
public class PermissionEntity extends AbstractEntity implements Permission, Serializable {

    /**
     * Serial version id
     */
    private static final long serialVersionUID = -4258555983967199451L;
    
    private String name;
    
    private String wildcardExpression;
    
    private String description;
    
    private List<RolePermissionAssociationEntity> rolePermissionAssociations;
    
    protected PermissionEntity() {
    }
    
    @Column(name = "name", nullable = false, unique = true)
    @Override
    public String getName() {
        return name;
    }
    
    protected void setName(String name) {
        this.name = name;
    }

    @Column(name = "wildcard_expression", nullable = false)
    @Override
    public String getWildcardExpression() {
        return wildcardExpression;
    }
    
    protected void setWildcardExpression(String wildcardExpression) {
        this.wildcardExpression = wildcardExpression;
    }
    
    @Column(name = "description", nullable = false)
    @Override
    public String getDescription() {
        return description;
    }
    
    protected void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "permission")
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
                append(wildcardExpression).
                append(description).
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
        PermissionEntity other = (PermissionEntity) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(name, other.name).
                append(wildcardExpression, other.wildcardExpression).
                append(description, other.description).
                append(rolePermissionAssociations, other.rolePermissionAssociations).
                isEquals();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                appendSuper(super.toString()).
                append("name", name).
                append("wildcardExpression", wildcardExpression).
                append("description", description).
                append("rolePermissionAssociations", rolePermissionAssociations).
                toString();
    }
}
