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

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.panifex.platform.api.security.Account;

public final class AccountImplBuilder {

    private RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    
    private String username;
    private String password;
    private String passwordSalt;
    
    public AccountImplBuilder(Account account) {
        username = account.getUsername();
        passwordSalt = getRandomPasswordSalt();
        password = getHashedPasswordBase64(account.getPassword(), passwordSalt);
    }
    
    protected String getRandomPasswordSalt() {
        return rng.nextBytes().toBase64();
    }
    
    protected String getHashedPasswordBase64(String plainTextPassword, String passwordSalt) {
        // decode password salt
        byte[] salt = Base64.decode(passwordSalt);
            
        // hash salted password
        String hashedPasswordBase64 = new SimpleHash(
            PersistenceRealm.HASH_ALGORITHM, 
            plainTextPassword, 
            salt, 
            PersistenceRealm.HASH_ITERATIONS).toBase64();
        
        return hashedPasswordBase64;
    }
    
    public AccountEntity build() {
        AccountEntity account = new AccountEntity(
            username,
            password,
            passwordSalt);
        
        return account;
    }
}
