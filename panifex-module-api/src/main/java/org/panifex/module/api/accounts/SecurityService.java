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

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.realm.Realm;

/**
 * A SecurityService is a security component that can access application-specific security
 * entities such as users, roles, and permissions to determine authentication and authorization operations.
 *
 * @see {@link org.apache.shiro.authz.Authorizer Authorizer}
 * @see {@link org.apache.shiro.realm.Realm Realm}
 *
 * @since 1.0
 * @deprecated replaced with authentication and authorization services
 */
@Deprecated
public interface SecurityService extends Authorizer, Realm {

    /**
     * Updates the {@link Account}'s expired password.
     * <p>
     * It must be implemented if the account can expire.
     *
     * @param username the {@link Account}'s username
     * @param oldPassword the current old {@link Account}'s password
     * @param newPassword the new {@link Account}'s plain password
     *
     * @throws AccountNotExpiredException if the {@link Account} has not expired
     * @throws UnknownAccountException if the {@link Account} with the same username does not exists in db
     * @throws IncorrectCredentialsException if the oldPassword does not match the current account's credentials
     *
     * @since 1.0
     */
    void updateAccountExpiredPassword(String username, String oldPassword, String newPassword)
        throws AccountNotExpiredException,
            IncorrectCredentialsException,
            UnknownAccountException;

}
