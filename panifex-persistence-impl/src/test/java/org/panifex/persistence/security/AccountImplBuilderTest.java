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
import org.apache.shiro.crypto.hash.SimpleHash;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.panifex.platform.api.security.Account;
import org.panifex.platform.persistence.security.AccountImpl;
import org.panifex.platform.persistence.security.AccountImplBuilder;
import org.panifex.platform.persistence.security.PersistenceRealm;

public final class AccountImplBuilderTest {

    @Test
    public void buildAccountTest() {
        // create mocks
        Account accountMock = EasyMock.createMock(Account.class);
        
        // username
        final String username = "admin";
        EasyMock.expect(accountMock.getUsername()).andReturn(username);
        
        // password
        final String plainTextPassword = "admin";
        EasyMock.expect(accountMock.getPassword()).andReturn(plainTextPassword);
        
        EasyMock.replay(accountMock);
        
        // build account impl
        AccountImplBuilder builder = new AccountImplBuilder(accountMock);
        AccountImpl buildedAccount = builder.build();
        
        EasyMock.verify(accountMock);
        
        // asserts
        Assert.assertEquals(username, buildedAccount.getUsername());
        
        // check password
        String passwordSalt = buildedAccount.getPasswordSalt();
        Assert.assertNotNull(passwordSalt);
        byte[] salt = Base64.decode(passwordSalt);
        String hashedPasswordBase64 = new SimpleHash(PersistenceRealm.HASH_ALGORITHM, plainTextPassword, salt, PersistenceRealm.HASH_ITERATIONS).toBase64();
        Assert.assertEquals(hashedPasswordBase64, buildedAccount.getPassword());

    }
}
