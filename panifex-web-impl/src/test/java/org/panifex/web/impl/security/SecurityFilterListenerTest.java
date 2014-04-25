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
 * Tests binding and unbinding realms to SecurityFilterListener
 *
 * TODO
 */
public class SecurityFilterListenerTest extends TestSupport {

    private SecurityFilterListener listener = new SecurityFilterListener();
    
    // mocks
    private SecurityFilter securityFilterMock = createMock(SecurityFilter.class);
    
    @Before
    public void setUp() {
        resetAll();
        listener.setSecurityFilter(securityFilterMock);
    }
    
    @Test
    public void bindSecurityServiceTest() {
        // create mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        replay(securityServiceMock);
        
        // bind realm
        listener.bind(securityServiceMock);
        
        verify(securityServiceMock);
    }
    
    @Test
    public void unbindSecurityServiceTest() {
        // create mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        replay(securityServiceMock);
        
        // unbind realm
        listener.unbind(securityServiceMock);
        
        verify(securityServiceMock);
    }
}
