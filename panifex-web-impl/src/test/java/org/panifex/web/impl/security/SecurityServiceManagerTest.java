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
import org.panifex.service.api.security.SecurityService;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link SecurityServiceManager} implementation.
 *
 */
public final class SecurityServiceManagerTest extends TestSupport {

    private SecurityServiceManager manager;
    
    /**
     * Initializes the {@link SecurityServiceManager} implementation before
     * performing further tests.
     */
    @Before
    public void setUp() {
        // initialize the manager
        manager = SecurityServiceManager.init();
    }
    
    /**
     * This test case initializes the manager again. The security service manager must
     * return the same instance because it is a singleton.
     */
    @Test
    public void initTest() {
        SecurityServiceManager manager = SecurityServiceManager.init();
        
        // manager must not be null
        assertNotNull(manager);
        
        // it must be equal with the previous instance
        assertEquals(this.manager, manager);
    }
    
    /**
     * Tries to bind the SecurityService to the manager.
     */
    @Test
    public void bindSecurityServiceTest() {
        // mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        replayAll();
        
        // bind the security service mock
        manager.bind(securityServiceMock);
        
        verifyAll();
        
        // check if the security service is properly binded
        SecurityService bindedSecurityService = SecurityServiceManager.getService();
        
        // binded security service must be the same as mocked service
        assertEquals(securityServiceMock, bindedSecurityService);
    }
    
    /**
     * Tries to unbind the security service. 
     */
    @Test
    public void unbindSecurityServiceTest() {
        // mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        replayAll();
        
        // bind the mocked security service
        manager.bind(securityServiceMock);
        
        // the mocked security service must not be null
        assertNotNull(SecurityServiceManager.getService());
        
        // unbind the mocked security service
        manager.unbind(securityServiceMock);
        
        verifyAll();
        
        // manager must not contain the references security service
        assertNull(SecurityServiceManager.getService());
    }
}
