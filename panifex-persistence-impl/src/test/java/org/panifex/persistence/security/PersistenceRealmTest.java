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

import javax.persistence.EntityManager;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.service.api.security.AccountNotExpiredException;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * This test cases checks the functionalities of {@link PersitenceRealm}.
 * <p>
 * It simulates successful and unsuccessful logging in various scenarios, and tests
 * updating the expired account's credentials.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    PersistenceRealm.class,
    Base64.class
})
public final class PersistenceRealmTest extends TestSupport {

    private PersistenceRealm realm;
    
    // mocks
    private AccountRepositoryImpl accountRepositoryMock = 
            createMock(AccountRepositoryImpl.class);
    private EntityManager entityManagerMock = createMock(EntityManager.class);
    private SecureRandomNumberGenerator randomGeneratorMock = 
            createMock(SecureRandomNumberGenerator.class);
    
    // constants
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String PASSWORD_HASH = "lA2jtiWHI3Yp35rRL/50CzAvGvTVb2PsULtZ3GeKdtDwS8V54sUAQ3g7wF6KCT3wE9OMRXLtTQ/PLJjbEaF4yg==";
    private final String PASSWORD_SALT = "1dfpY4vWFRtQIqOxejFVJg==";
    
    @Before
    public void before() throws Exception {
        // expect instancing the SecureRandomNumberGenerator
        expectNew(SecureRandomNumberGenerator.class).andReturn(randomGeneratorMock);
        
        replayAll();
        
        // create realm
        realm = new PersistenceRealm();
        
        // set the realm's properties
        realm.setAccountRepository(accountRepositoryMock);
        realm.setEntityManager(entityManagerMock);
        
        verifyAll();
        resetAll();
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
 
        expect(accountRepositoryMock.getAccountByUsername(entityManagerMock, USERNAME)).andReturn(accountMock);
        expect(accountMock.getUsername()).andReturn(USERNAME);
        expect(accountMock.getPassword()).andReturn(PASSWORD_HASH).times(2);
        expect(accountMock.getPasswordSalt()).andReturn(PASSWORD_SALT).times(2);
        expect(accountMock.getIsCredentialsExpired()).andReturn(false);
        
        replayAll();
        
        // authentificate token
        AuthenticationInfo info = realm.getAuthenticationInfo(tokenMock);
        
        verifyAll();
        
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
 
        expect(accountRepositoryMock.getAccountByUsername(entityManagerMock, USERNAME)).andReturn(null);
        
        replayAll();

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
 
        expect(accountRepositoryMock.getAccountByUsername(entityManagerMock, USERNAME)).andReturn(accountMock);
        expect(accountMock.getUsername()).andReturn(USERNAME);
        expect(accountMock.getPassword()).andReturn(PASSWORD_HASH);
        expect(accountMock.getPasswordSalt()).andReturn(PASSWORD_SALT);
        expect(accountMock.getIsCredentialsExpired()).andReturn(true);
        
        replayAll();

        try {
            // authentificate token
            realm.getAuthenticationInfo(tokenMock);
        } catch (ExpiredCredentialsException e) {
            // expected 
            verifyAll();
            return;
        }
        
        fail("ExpiredCredentialException have must be thrown");
    }
    
    /**
     * Tries to successfully update an accounts' expired password.
     * <p>
     * The account's password must have been updated.
     */
    @Test
    public void successfullyUpdateExpiredPasswordTest() throws Exception {
        String username = getRandomWord();
        String plainTextPassword = getRandomChars(20);
        String base64PasswordSalt = getRandomChars(20);
        byte[] bytesPasswordSalt = base64PasswordSalt.getBytes();
        String hashedSaltedPassword = getRandomChars(20);
        
        // create mocks
        AccountEntity accountMock = createMock(AccountEntity.class);
        ByteSource byteSourceMock = createMock(ByteSource.class);
        SimpleHash simpleHashMock = createMockAndExpectNew(SimpleHash.class,
            new Class[] {String.class, Object.class, Object.class, int.class},
            PersistenceRealm.HASH_ALGORITHM,
            plainTextPassword,
            bytesPasswordSalt,
            PersistenceRealm.HASH_ITERATIONS);
        mockStatic(Base64.class);
        
        // expectations
        
        // expect get the account from the repository
        expect(accountRepositoryMock.getAccountByUsername(entityManagerMock, username)).andReturn(accountMock);
        
        // expect checking if the account is existed and if it is expired
        expect(accountMock.getIsCredentialsExpired()).andReturn(true);
        expect(accountMock.getPassword()).andReturn(PASSWORD_HASH);
        expect(accountMock.getPasswordSalt()).andReturn(PASSWORD_SALT);
        
        // expect generating a password salt and decoding it to Base64
        expect(randomGeneratorMock.nextBytes()).andReturn(byteSourceMock);
        expect(byteSourceMock.toBase64()).andReturn(base64PasswordSalt);
        expect(Base64.decode(base64PasswordSalt)).andReturn(bytesPasswordSalt);
        expect(simpleHashMock.toBase64()).andReturn(hashedSaltedPassword);
        
        // expect setting a hashed password and the password salt to the account entity
        accountMock.setPassword(hashedSaltedPassword);
        accountMock.setPasswordSalt(base64PasswordSalt);
        accountMock.setIsCredentialsExpired(false);
        
        // expect persisting the changed account
        accountRepositoryMock.updateAccount(entityManagerMock, accountMock);
        
        replayAll();
        
        // try to update the account's password
        realm.updateAccountExpiredPassword(username, plainTextPassword);
        
        verifyAll();
    }
    
    /**
     * Tries to update a not existed accounts' password. {@link org.panifex.service.api.security.AccountNotFoundException AccountNotFoundException}
     * must be thrown because the account with the same username does not exist in db.
     */
    @Test(expected = UnknownAccountException.class)
    public void updateNotExistedAccountsPasswordTest() throws UnknownAccountException, AccountNotExpiredException {
        // variables
        String username = getRandomWord();
        String plainTextPassword = getRandomChars(20);
        
        // expectations
        
        // expect getting the account from the repository
        expect(accountRepositoryMock.getAccountByUsername(entityManagerMock, username)).andReturn(null);
        
        replayAll();
        
        // try to update the account's password
        realm.updateAccountExpiredPassword(username, plainTextPassword);
        
        fail("AccountNotFoundException must be thrown");
    }
    
    /**
     * Tries to update a not expired account. {@link org.panifex.service.api.security.AccountNotExpiredException AccountNotExpiredException}
     * must be thrown because the account is not expired.
     */
    @Test(expected = AccountNotExpiredException.class)
    public void updateNotExpiredAccountTest() throws UnknownAccountException, AccountNotExpiredException {
        // variables
        String username = getRandomWord();
        String plainTextPassword = getRandomChars(20);
        
        // mocks
        AccountEntity accountEntity = createMock(AccountEntity.class);
        
        // expectations
        
        // expect getting the account from the repository
        expect(accountRepositoryMock.getAccountByUsername(entityManagerMock, username)).andReturn(accountEntity);
        
        // expect checking if the account is existed and it is expired
        expect(accountEntity.getIsCredentialsExpired()).andReturn(false);
        expect(accountEntity.getPassword()).andReturn(PASSWORD_HASH);
        expect(accountEntity.getPasswordSalt()).andReturn(PASSWORD_SALT);
        
        replayAll();
        
        // try to update the account's password
        realm.updateAccountExpiredPassword(username, plainTextPassword);
        
        fail("AccountNotExpiredException must be thrown.");
    }
}
