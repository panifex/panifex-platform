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

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Test;
import org.panifex.service.api.security.SecurityService;
import org.panifex.test.support.TestSupport;

/**
 * Tests binding and unbinding security filters to SecurityFilterListener
 *
 */
public class SecurityFilterListenerBindingSecurityFilterTest extends TestSupport {

    private SecurityFilterListener listener = new SecurityFilterListener();

    /**
     * This test case try to simulate situation when SecurityFilter bean appears.
     * 
     * <p> In that situation, listener must register OsgiRealm to their SecurityManager. 
     */
    @Test
    public void bindSecurityFilterTest() {
        // create mocks
        SecurityFilter secFilterMock = createMock(SecurityFilter.class);
        DefaultWebSecurityManager secManagerMock = createMock(DefaultWebSecurityManager.class);
        SecurityService secServiceMock = createMock(SecurityService.class);
        Set<Realm> realms = new HashSet<>();
        realms.add(secServiceMock);
        
        // must get SecurityManager in order to set osgi realm
        expect(secFilterMock.getSecurityManager()).andReturn(secManagerMock);
        
        // set osgi realm to security manager
        secManagerMock.setRealms(realms);
        
        replay(secServiceMock, secFilterMock, secManagerMock);
        
        // bind realm
        listener.bind(secServiceMock);
        
        // bind security filter
        listener.bind(secFilterMock);
        
        verify(secServiceMock, secFilterMock, secManagerMock);
    }
    
    /**
     * Simulates situation when SecurityFilter stopping been available
     */
    @Test
    public void unbindSecurityFilterTest() {
        // create mocks
        SecurityFilter secFilter = createMock(SecurityFilter.class);
        
        replay(secFilter);
        
        // unbind security filter
        listener.unbind(secFilter);
        
        verify(secFilter);
    }
}
