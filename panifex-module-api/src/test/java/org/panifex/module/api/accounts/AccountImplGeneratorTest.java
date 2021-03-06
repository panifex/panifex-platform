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
package org.panifex.module.api.accounts;

import org.junit.Test;
import org.panifex.module.api.accounts.AccountImpl;
import org.panifex.test.support.TestSupport;

/**
 * Unit test for the {@link AccountImplGenerator} class.
 */
public final class AccountImplGeneratorTest extends TestSupport {

    /**
     * This test checks generating random persisted {@link AccountImpl} instances.
     */
    @Test
    public void generateAccountImplTest() {
        AccountImpl account = AccountImplGenerator.generatePersistedAccount();
    
        assertNotNull(account.getId());
        assertNotNull(account.getOptlockVersion());
        assertNotNull(account.getUsername());
        assertNotNull(account.getPassword());
    }
}
