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

import org.junit.Test;
import org.panifex.service.api.security.AccountImpl;
import org.panifex.service.api.security.AccountImplGenerator;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link AccountEntityBuilder} class.
 */
public final class AccountEntityBuilderTest extends TestSupport {

    @Test
    public void buildAccountEntityTest() {
        AccountImpl accountImpl = AccountImplGenerator.generatePersistedAccount();
        
        AccountEntityBuilder builder = new AccountEntityBuilder(accountImpl);
        
        AccountEntity accountEntity = builder.build();
        
        assertEquals(accountImpl.getId(), accountEntity.getId());
        assertEquals(accountImpl.getOptlockVersion(), accountEntity.getOptlockVersion());
    }
}
