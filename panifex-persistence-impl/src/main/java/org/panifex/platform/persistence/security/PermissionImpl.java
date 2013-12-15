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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.panifex.platform.api.security.Permission;

@Entity
@StaticMetamodel(PermissionImpl_.class)
@Table(name = "sec_permission")
public class PermissionImpl implements Permission, Serializable {

    /**
     * Serial version id
     */
    private static final long serialVersionUID = -4258555983967199451L;

    @Id
    @Column(name = "permission_id", nullable = false)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "wildcard_expression", nullable = false)
    private String wildcardExpression;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @ManyToMany(mappedBy = "permissions")
    private List<RoleImpl> roles;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getWildcardExpression() {
        return wildcardExpression;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

}
