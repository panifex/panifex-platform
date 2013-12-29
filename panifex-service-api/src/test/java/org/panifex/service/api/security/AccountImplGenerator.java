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
package org.panifex.service.api.security;

import org.fluttercode.datafactory.impl.DataFactory;
import org.panifex.service.api.EntityImpl;
import org.panifex.service.api.security.AccountImpl;

public final class AccountImplGenerator {

    /**
     * Private constructor protects {@link EntityImpl EntityImpl} from instancing - singleton.
     */
    private AccountImplGenerator() {
    }
    
    /**
     * Returns generated persisted {@link AccountImpl AccountImpl}.
     * 
     * @return generated persisted {@link AccountImpl AccountImpl}
     */
    public static AccountImpl generatePersistedAccount() {
        DataFactory df = new DataFactory();
        
        AccountImpl account = new AccountImpl(
            new Long(df.getNumberBetween(0, Integer.MAX_VALUE)),
            df.getNumberBetween(0, Integer.MAX_VALUE),
            df.getRandomWord(),
            df.getRandomText(255),
            false);
        return account;
    }
}
