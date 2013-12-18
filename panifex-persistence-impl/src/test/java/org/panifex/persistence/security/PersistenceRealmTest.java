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
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.panifex.platform.persistence.security.AccountEntity;
import org.panifex.platform.persistence.security.AccountRepository;
import org.panifex.platform.persistence.security.PersistenceRealm;

public final class PersistenceRealmTest {

    private PersistenceRealm realm;
    
    // mocks
    private AccountRepository accountRepositoryMock;

    @Before
    public void before() {
        realm = new PersistenceRealm();
        accountRepositoryMock = EasyMock.createMock(AccountRepository.class);
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
        AccountEntity accountMock = EasyMock.createMock(AccountEntity.class);
        UsernamePasswordToken tokenMock = EasyMock.createMock(UsernamePasswordToken.class);
        
        // username
        String username = "admin";
        EasyMock.expect(tokenMock.getUsername()).andReturn(username);
        
        // password
        String password = "admin";
        EasyMock.expect(tokenMock.getCredentials()).andReturn(password.toCharArray());
        
        EasyMock.expect(accountRepositoryMock.getAccountByUsername(username)).andReturn(accountMock);
        EasyMock.expect(accountMock.getPassword()).andReturn("lA2jtiWHI3Yp35rRL/50CzAvGvTVb2PsULtZ3GeKdtDwS8V54sUAQ3g7wF6KCT3wE9OMRXLtTQ/PLJjbEaF4yg==");
        EasyMock.expect(accountMock.getPasswordSalt()).andReturn("1dfpY4vWFRtQIqOxejFVJg==");
        
        EasyMock.replay(accountMock, accountRepositoryMock, tokenMock);
        
        // authenticate token
        AuthenticationInfo info = realm.getAuthenticationInfo(tokenMock);
        
        EasyMock.verify(accountMock, accountRepositoryMock, tokenMock);
        
        
        Assert.assertNotNull(info);
    }
    
}
