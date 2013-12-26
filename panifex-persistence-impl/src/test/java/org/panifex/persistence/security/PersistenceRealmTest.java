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
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;

/**
 * This test cases checks the functionalities of {@link PersitenceRealm}.
 * <p>
 * It simulates successful and unsuccessful logging in scenarios.
 */
public final class PersistenceRealmTest extends TestSupport {

    private PersistenceRealm realm;
    
    // mocks
    private AccountRepositoryImpl accountRepositoryMock;

    // constants
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String PASSWORD_HASH = "lA2jtiWHI3Yp35rRL/50CzAvGvTVb2PsULtZ3GeKdtDwS8V54sUAQ3g7wF6KCT3wE9OMRXLtTQ/PLJjbEaF4yg==";
    private final String PASSWORD_SALT = "1dfpY4vWFRtQIqOxejFVJg==";
    
    @Before
    public void before() {
        realm = new PersistenceRealm();
        accountRepositoryMock = createMock(AccountRepositoryImpl.class);
        realm.setAccountRepository(accountRepositoryMock);
    }
    
    /**
     * Simulates the successful authentification process through {@link PersistenceRealm}.
     * <p>
     * It creates an authentification token and tries to resolve AuthentificationInfo
     * from {@link PersistenceRealm}. The token must be resolved.
     */
    @Test
    public void successfulAuthentificationTest() {
        // create mocks
        AccountEntity accountMock = createMock(AccountEntity.class);
        UsernamePasswordToken tokenMock = createMock(UsernamePasswordToken.class);
        
        // expectations
        expect(tokenMock.getUsername()).andReturn(USERNAME);
        expect(tokenMock.getCredentials()).andReturn(PASSWORD.toCharArray());
 
        expect(accountRepositoryMock.getAccountByUsername(USERNAME)).andReturn(accountMock);
        expect(accountMock.getPassword()).andReturn(PASSWORD_HASH);
        expect(accountMock.getPasswordSalt()).andReturn(PASSWORD_SALT);
        expect(accountMock.getIsCredentialsExpired()).andReturn(false);
        
        replay(accountMock, accountRepositoryMock, tokenMock);
        
        // authentificate token
        AuthenticationInfo info = realm.getAuthenticationInfo(tokenMock);
        
        verify(accountMock, accountRepositoryMock, tokenMock);
        
        assertNotNull(info);
    }
    
    /**
     * Simulates the unsuccessful authentication process through {@link PersistenceRealm},
     * because the wrong username of password.
     * <p>
     * {@link org.apache.shiro.authc.UnknownAccountException} must be thrown during the process.
     */
    @Test
    public void unknownAccountTest() {
     // create mocks
        UsernamePasswordToken tokenMock = createMock(UsernamePasswordToken.class);
        
        // expectations
        expect(tokenMock.getUsername()).andReturn(USERNAME);
 
        expect(accountRepositoryMock.getAccountByUsername(USERNAME)).andReturn(null);
        
        replay(accountRepositoryMock, tokenMock);

        try {
            // authentificate token
            realm.getAuthenticationInfo(tokenMock);
        } catch (UnknownAccountException e) {
            // expected 
            verify(accountRepositoryMock, tokenMock);
            return;
        }
        
        fail("UnknownAccountException have must be thrown");
    }
    
    /**
     * Simulates the unsuccessful authentication process through {@link PersistenceRealm},
     * because the account has been expired.
     * <p>
     * {@link org.apache.shiro.authc.ExpiredCredentialsException} must be thrown during the process.
     */
    @Test
    public void expiredCredentialsTest() {
        // create mocks
        AccountEntity accountMock = createMock(AccountEntity.class);
        UsernamePasswordToken tokenMock = createMock(UsernamePasswordToken.class);
        
        // expectations
        expect(tokenMock.getUsername()).andReturn(USERNAME);
 
        expect(accountRepositoryMock.getAccountByUsername(USERNAME)).andReturn(accountMock);
        expect(accountMock.getPassword()).andReturn(PASSWORD_HASH);
        expect(accountMock.getPasswordSalt()).andReturn(PASSWORD_SALT);
        expect(accountMock.getIsCredentialsExpired()).andReturn(true);
        
        replay(accountMock, accountRepositoryMock, tokenMock);

        try {
            // authentificate token
            realm.getAuthenticationInfo(tokenMock);
        } catch (ExpiredCredentialsException e) {
            // expected 
            verify(accountMock, accountRepositoryMock, tokenMock);
            return;
        }
        
        fail("ExpiredCredentialException have must be thrown");
    }
}
