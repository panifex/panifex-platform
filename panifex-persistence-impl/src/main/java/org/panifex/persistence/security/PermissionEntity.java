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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.panifex.persistence.AbstractEntity;
import org.panifex.service.api.security.Permission;

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
}
