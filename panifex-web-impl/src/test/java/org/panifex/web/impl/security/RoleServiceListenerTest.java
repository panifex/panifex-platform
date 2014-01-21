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
package org.panifex.web.impl.security;

import org.junit.Before;
import org.junit.Test;
import org.panifex.service.api.security.RoleService;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link RoleServiceListener} class.
 */
public final class RoleServiceListenerTest extends TestSupport {

    private RoleServiceListener listener;
    
    /**
     * Initializes the {@link RoleServiceListener} before
     * performing further tests.
     */
    @Before
    public void setUp() {
        // initialize the listener
        listener = RoleServiceListener.init();
    }
    
    /**
     * This test case initializes the listener again. It must
     * return the same instance because it is a singleton.
     */
    @Test
    public void initTest() {
        RoleServiceListener listener = RoleServiceListener.init();
        
        // the listener must not be null
        assertNotNull(listener);
        
        // it must be equal with the previous instance
        assertEquals(this.listener, listener);
    }
    
    /**
     * Tries to bind the RoleService to the listener.
     */
    @Test
    public void bindRoleServiceTest() {
        // mocks
        RoleService roleServiceMock = createMock(RoleService.class);
        
        replayAll();
        
        // bind the role service mock
        listener.bind(roleServiceMock);
        
        verifyAll();
        
        // check if the role service is properly binded
        RoleService bindedService = RoleServiceListener.getService();
        
        // binded service must be the same as mocked service
        assertEquals(roleServiceMock, bindedService);
    }
    
    /**
     * Tries to unbind the role service. 
     */
    @Test
    public void unbindRoleServiceTest() {
        // mocks
        RoleService roleServiceMock = createMock(RoleService.class);
        
        replayAll();
        
        // bind the mocked service
        listener.bind(roleServiceMock);
        
        // the mocked service must not be null
        assertNotNull(RoleServiceListener.getService());
        
        // unbind the mocked service
        listener.unbind(roleServiceMock);
        
        verifyAll();
        
        // listener must not contain the referenced service
        assertNull(RoleServiceListener.getService());
    }
}
