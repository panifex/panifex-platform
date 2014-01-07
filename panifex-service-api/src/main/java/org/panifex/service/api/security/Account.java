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

import org.panifex.service.api.Entity;

/**
 * A user's account.
 * <p>
 * It contains username and password stored account information 
 * for signing-in process.
 * 
 * @since 1.0
 */
public interface Account extends Entity {

    /**
     * Returns the account's username
     * 
     * @return the account's username
     * 
     * @since 1.0
     */
    String getUsername();

    /**
     * Returns the account's password. 
     * <p>
     * The password can be hashed, salted and Base64 encoded in case
     * getting it from a repository, or in plain text in case saving it
     * to the repository.
     * 
     * @return the account's password
     * 
     * @since 1.0
     */
    String getPassword();

    /**
     * Sets the account's password.
     * 
     * @param password the plain text password
     * 
     * @since 1.0
     */
    void setPassword(String password);
    
    /**
     * Returns true if credentials are expired and a user must to change it
     * before logging in.
     * 
     * @return true if the user must to change credentials before logging in
     * 
     * @since 1.0
     */
    boolean getIsCredentialsExpired();
}
