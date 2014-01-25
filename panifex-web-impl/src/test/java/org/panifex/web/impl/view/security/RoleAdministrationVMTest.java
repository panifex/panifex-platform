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
package org.panifex.web.impl.view.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.service.api.security.RoleImpl;
import org.panifex.service.api.security.RoleService;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.security.RoleServiceListener;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zul.ListModel;

/**
 * Unit tests for the {@link RoleAdministrationVM} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    RoleServiceListener.class})
public final class RoleAdministrationVMTest extends TestSupport {

    private RoleAdministrationVM vm = new RoleAdministrationVM();
    
    /**
     * Prepares an environment for executing unit tests.
     */
    @Before
    public void setUp() {
        // mocks
        mockStatic(RoleServiceListener.class);
        
        resetAll();
    }
    
    /**
     * This test check the {@link RoleAdministrationVM#getRoles()} method
     * in case the RoleService implementation is active.
     * <p>
     * The roles must be returned.
     */
    @Test
    public void getRolesFromActiveServiceTest() {
        // variables
        List<RoleImpl> persistedRoles = new ArrayList<>();
        
        // mocks
        RoleService roleServiceMock = createMock(RoleService.class);
        
        // expect getting the active RoleService
        expect(RoleServiceListener.getService()).andReturn(roleServiceMock);
        
        // expect getting the persisted roles
        expect(roleServiceMock.getRoles()).andReturn(persistedRoles);
        
        // perform test
        replayAll();
        ListModel<RoleImpl> roles = vm.getRoles();
        verifyAll();
        
        // the roles must not be null
        assertNotNull(roles);
    }
    
    /**
     * This test check the {@link RoleAdministrationVM#getRoles()} method 
     * in case the RoleService implementation is not active.
     * <p>
     * The nothing (null) must be returned.
     */
    @Test
    public void getRolesFromInactiveServiceTest() {
        // expect getting the inactive RoleService
        expect(RoleServiceListener.getService()).andReturn(null);
        
        // perform test
        replayAll();
        ListModel<RoleImpl> roles = vm.getRoles();
        verifyAll();
        
        // the roles must be null
        assertNull(roles);
    }
}
