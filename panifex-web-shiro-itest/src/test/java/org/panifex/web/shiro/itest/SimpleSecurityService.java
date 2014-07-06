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
package org.panifex.web.shiro.itest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.panifex.service.api.security.AccountNotExpiredException;
import org.panifex.service.api.security.SecurityService;

/**
 * A simple security service that uses a set of configured user accounts and roles to
 * support authentication and authorization.  Each account entry specifies the username, password,
 * and roles for a user.  Roles can also be mapped to permissions and associated with users.
 */
public class SimpleSecurityService extends SimpleAccountRealm implements SecurityService {

    @Override
    public void updateAccountExpiredPassword(String username,
            String oldPassword, String newPassword)
            throws AccountNotExpiredException, IncorrectCredentialsException,
            UnknownAccountException {
    }
}