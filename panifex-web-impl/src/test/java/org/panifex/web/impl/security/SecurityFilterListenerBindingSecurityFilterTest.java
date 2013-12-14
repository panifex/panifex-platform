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
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests binding and unbinding security filters to SecurityFilterListener
 *
 */
public class SecurityFilterListenerBindingSecurityFilterTest {

    private SecurityFilterListener listener = new SecurityFilterListener();
    
    private OsgiRealm osgiRealmMock;
    
    @Before
    public void before() {
        // set mocked osgi realm
        osgiRealmMock = EasyMock.createMock(OsgiRealm.class);
        listener.setOsgiRealm(osgiRealmMock);
    }
    
    /**
     * This test case try to simulate situation when SecurityFilter bean appears.
     * 
     * <p> In that situation, listener must register OsgiRealm to their SecurityManager. 
     */
    @Test
    public void bindSecurityFilterTest() {
        // create mocks
        SecurityFilter secFilter = EasyMock.createMock(SecurityFilter.class);
        DefaultWebSecurityManager secManager = EasyMock.createMock(DefaultWebSecurityManager.class);
        Set<Realm> realms = new HashSet<>();
        
        // must get SecurityManager in order to set osgi realm
        EasyMock.expect(secFilter.getSecurityManager()).andReturn(secManager);
        
        // set osgi realm to security manager
        secManager.setRealms(realms);
        
        EasyMock.replay(osgiRealmMock, secFilter, secManager);
        
        // bind security filter
        listener.bind(secFilter);
        
        EasyMock.verify(osgiRealmMock, secFilter, secManager);
    }
    
    /**
     * Simulates situation when SecurityFilter stopping been available
     */
    @Test
    public void unbindSecurityFilterTest() {
        // create mocks
        SecurityFilter secFilter = EasyMock.createMock(SecurityFilter.class);
        
        EasyMock.replay(osgiRealmMock, secFilter);
        
        // unbind security filter
        listener.unbind(secFilter);
        
        EasyMock.verify(osgiRealmMock, secFilter);
    }
}
