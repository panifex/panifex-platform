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

import java.util.List;

import org.junit.Test;
import org.panifex.security.persistence.RoleEntity;
import org.panifex.security.persistence.RoleRepository;
import org.panifex.security.persistence.RoleRepositoryImpl;
import org.panifex.security.persistence.RoleServiceImpl;

/**
 * Unit tests for the {@link RoleRepositoryImpl} class.
 */
public final class RoleRepositoryImplTest extends RepositoryTestSupport {

    private RoleRepository roleRepository = new RoleRepositoryImpl();
    
    /**
     * This test checks the {@link RoleServiceImpl#getRoles()} method.
     * <p>
     * It must fetch roles from the {@link RoleRepository}.
     */
    @Test
    public void getAllRolesTest() throws Exception {
        // get all roles
        List<RoleEntity> roles = roleRepository.getRoles(entityManager);
        
        // it must be 3 roles ordered by name
        assertNotNull(roles);
        assertEquals(3, roles.size());
        
        // first role must be 'Administrator'
        assertEquals("Administrator", roles.get(0).getName());
        
        // second role must be 'Role 1'
        assertEquals("Role 1", roles.get(1).getName());
        assertEquals("Role 1 - Description", roles.get(1).getDescription());
        
        // third role must be 'Role 2'
        assertEquals("Role 2", roles.get(2).getName());
        assertEquals("Role 2 - Description", roles.get(2).getDescription());
        
    }
}
