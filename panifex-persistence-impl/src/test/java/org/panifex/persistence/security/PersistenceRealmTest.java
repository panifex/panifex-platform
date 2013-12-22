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

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;

public final class PersistenceRealmTest extends TestSupport {

    private PersistenceRealm realm;
    
    // mocks
    private AccountRepositoryImpl accountRepositoryMock;

    @Before
    public void before() {
        realm = new PersistenceRealm();
        accountRepositoryMock = createMock(AccountRepositoryImpl.class);
        realm.setAccountRepository(accountRepositoryMock);
    }
    
    /**
     * This test simulates authentification process through PersistenceRealm.
     * 
     * <p>For starter it creates authentification token and tries to resolve AuthentificationInfo
     * from PersistenceRealm.
     */
    @Test
    public void authentificationTest() {
        // create mocks
        AccountEntity accountMock = createMock(AccountEntity.class);
        UsernamePasswordToken tokenMock = createMock(UsernamePasswordToken.class);
        
        // username
        String username = "admin";
        expect(tokenMock.getUsername()).andReturn(username);
        
        // password
        String password = "admin";
        expect(tokenMock.getCredentials()).andReturn(password.toCharArray());
        
        expect(accountRepositoryMock.getAccountByUsername(username)).andReturn(accountMock);
        expect(accountMock.getPassword()).andReturn("lA2jtiWHI3Yp35rRL/50CzAvGvTVb2PsULtZ3GeKdtDwS8V54sUAQ3g7wF6KCT3wE9OMRXLtTQ/PLJjbEaF4yg==");
        expect(accountMock.getPasswordSalt()).andReturn("1dfpY4vWFRtQIqOxejFVJg==");
        
        replay(accountMock, accountRepositoryMock, tokenMock);
        
        // authenticate token
        AuthenticationInfo info = realm.getAuthenticationInfo(tokenMock);
        
        verify(accountMock, accountRepositoryMock, tokenMock);
        
        
        assertNotNull(info);
    }
    
}
