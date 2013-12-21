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

import java.util.List;

import org.panifex.service.api.security.Account;

/**
 * Repository of {@link AccountEntity}.
 *
 */
public interface AccountRepository {

    void insertAccount(Account account);
    
    AccountEntity getAccountByUsername(String username);
    
    List<PermissionEntity> getPermissionsByAccount(Account account);
    
    /**
     * Returns a distinct list of {@link RoleEntity} for specified account.
     * 
     * <p>If the account does not have any role, than the empty list is returned.
     * 
     * @param account the account whose roles will be returned
     * @return a distinct list of {@link RoleEntity} for specified account
     */
    List<RoleEntity> getRolesForAccount(Account account);
}
