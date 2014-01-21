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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.service.api.security.RoleImpl;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for the {@link RoleServiceImpl} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    RoleImpl.class,
    RoleImplBuilder.class,
    RoleServiceImpl.class})
public final class RoleServiceImplTest extends TestSupport {

    // tested class
    private RoleServiceImpl roleService;
    
    // mocks
    private EntityManager entityManagerMock;
    private RoleRepository roleRepositoryMock;
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        // mocks
        entityManagerMock = createMock(EntityManager.class);
        roleRepositoryMock = createMock(RoleRepository.class);
        
        // create service
        roleService = new RoleServiceImpl();
        
        // set the service properties
        roleService.setEntityManager(entityManagerMock);
        roleService.setRoleRepository(roleRepositoryMock);
        
        resetAll();
    }
    
    /**
     * This test checks the {@link RoleServiceImpl#getRoles()} method.
     * <p>
     * It must fetch roles from the {@link RoleRepository}.
     */
    @Test
    public void getAllRolesTest() throws Exception {
        // mocks
        RoleEntity roleEntity1 = createMock(RoleEntity.class);
        RoleEntity roleEntity2 = createMock(RoleEntity.class);
        RoleImpl roleImpl1 = createMock(RoleImpl.class);
        RoleImpl roleImpl2 = createMock(RoleImpl.class);
        RoleImplBuilder roleImplBuilderMock1 = createMockAndExpectNew(RoleImplBuilder.class, roleEntity1);
        RoleImplBuilder roleImplBuilderMock2 = createMockAndExpectNew(RoleImplBuilder.class, roleEntity2);
        
        // role entity collection
        List<RoleEntity> roleEntities = new ArrayList<>();
        roleEntities.add(roleEntity1);
        roleEntities.add(roleEntity2);
        
        // expect getting roles from the repository
        expect(roleRepositoryMock.getRoles(entityManagerMock)).andReturn(roleEntities);
        
        // expect building RoleImpl objects
        expect(roleImplBuilderMock1.build()).andReturn(roleImpl1);
        expect(roleImplBuilderMock2.build()).andReturn(roleImpl2);
        
        // perform test
        replayAll();
        List<RoleImpl> fetchedRoles = roleService.getRoles();
        verifyAll();

        // verify fetched roles collection
        assertNotNull(fetchedRoles);
        assertEquals(2, fetchedRoles.size());
        assertEquals(roleImpl1, fetchedRoles.get(0));
        assertEquals(roleImpl2, fetchedRoles.get(1));
    }
}
