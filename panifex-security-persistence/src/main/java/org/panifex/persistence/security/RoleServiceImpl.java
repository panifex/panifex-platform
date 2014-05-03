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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.panifex.service.api.security.Role;
import org.panifex.service.api.security.RoleImpl;
import org.panifex.service.api.security.RoleService;

/**
 * A RoleService is responsible for managing the {@link Role} entities.
 * <p>
 * This class must not be used directly. It should be used by 
 * {@link RoleService}
 * interface.
 */
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    
    private EntityManager entityManager;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    /**
     * Returns the collection of persisted {@link RoleImpl} objects.
     * 
     * @return the collection of persisted {@link RoleImpl} objects
     */
    @Override
    public List<RoleImpl> getRoles() {
        // get roles from the repository
        List<RoleEntity> roleEntities = roleRepository.getRoles(entityManager);
        
        // transform role entites to roles
        List<RoleImpl> roles = getRolesByRoleEntities(roleEntities);
        
        // return fetched roles        
        return roles;
    }
    
    /**
     * Transforms the collection of {@link RoleEntity} objects to the
     * collection of {@link Role} objects.
     * <p>
     * This method uses {@link RoleImplBuilder} class for building {@link RoleImpl}
     * @param roleEntities
     * @return
     */
    private List<RoleImpl> getRolesByRoleEntities(List<RoleEntity> roleEntities) {
        // create an empty collection
        List<RoleImpl> roles = new ArrayList<>();
        
        for (RoleEntity roleEntity : roleEntities) {
            // transform the role entity to role impl
            RoleImpl role = new RoleImplBuilder(roleEntity).build();
            
            // add transformed role to the collection
            roles.add(role);
        }
        
        return roles;
    }

}
