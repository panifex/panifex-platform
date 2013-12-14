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

import org.easymock.EasyMock;
import org.junit.Test;
import org.panifex.platform.api.security.SecurityService;

public class OsgiRealmTest {

    private OsgiRealm realm = new OsgiRealm();
    
    @Test
    public void bindSecurityServiceTest() {
        // create mocks
        SecurityService secService = EasyMock.createMock(SecurityService.class);
        
        EasyMock.replay(secService);
        
        // bind security service
        realm.bind(secService);
        
        EasyMock.verify(secService);
    }
    
    @Test
    public void unbindSecurityServiceTest() {
        // create mocks
        SecurityService secService = EasyMock.createMock(SecurityService.class);
        
        EasyMock.replay(secService);
        
        // unbind security service
        realm.unbind(secService);
        
        EasyMock.verify(secService);
    }
}
