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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.panifex.service.api.EntityImpl;

/**
 * An implementation of the {@link Account} interface which contains
 * username and password stored account information for signing-in 
 * process.
 * 
 * @since 1.0
 */
public final class AccountImpl extends EntityImpl implements Account {

    /**
     * The account's username.
     */
    private String username;
    
    /**
     * The account's password
     */
    private String password;
    
    /**
     * The flag which defines if the account's credentials have been
     * expired.
     */
    private boolean isCredentialsExpired;

    /**
     * Constructs a new {@link AccountImpl} instance for persisted
     * accounts.
     * 
     * @param id the {@link org.panifex.service.api.Entity Entity}'s ID
     * @param optlockVersion the {@link org.panifex.service.api.Entity Entity}'s version
     * @param username the account's username
     * @param password the account's password
     * @param isCredentialsExpired if the account's credentials have been expired
     * 
     * @since 1.0
     */
    public AccountImpl(
            Long id,
            int optlockVersion,
            String username,
            String password,
            boolean isCredentialsExpired) {
        super(id, optlockVersion);
        this.username = username;
        this.password = password;
    }
    
    /**
     * Returns the account's username
     * 
     * @return the account's username
     * 
     * @since 1.0
     */
    @Override
    public String getUsername() {
        return username;
    }

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
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the account's password.
     * 
     * @param password the plain text password
     * 
     * @since 1.0
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns true if credentials are expired and a user must to change it
     * before logging in.
     * 
     * @return true if the user must to change credentials before logging in
     * 
     * @since 1.0
     */
    @Override
    public boolean getIsCredentialsExpired() {
        return isCredentialsExpired;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                appendSuper(super.hashCode()).
                append(username).
                append(password).
                append(isCredentialsExpired).
                toHashCode();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AccountImpl other = (AccountImpl) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(username, other.username).
                append(password, other.password).
                append(isCredentialsExpired, other.isCredentialsExpired).
                isEquals();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                appendSuper(super.toString()).
                append("username", username).
                append("password", password).
                append("isCredentialsExpired", isCredentialsExpired).
                toString();
    }
}
