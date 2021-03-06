/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.itest.security.shiro.support;

import org.apache.shiro.realm.SimpleAccountRealm;
import org.panifex.module.api.security.AuthenticationService;

/**
 * A simple security service that uses a set of configured user accounts and roles to
 * support authentication and authorization.  Each account entry specifies the username, password,
 * and roles for a user.  Roles can also be mapped to permissions and associated with users.
 */
public class SimpleAuthenticationService extends SimpleAccountRealm implements AuthenticationService {
}
