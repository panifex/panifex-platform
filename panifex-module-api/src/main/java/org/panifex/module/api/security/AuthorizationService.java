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
package org.panifex.module.api.security;

import org.apache.shiro.authz.Authorizer;

/**
 * The AuthorizationService performs authorization (access control) operations for any given Subject (aka 'application user').
 * <p>
 * Each method requires a subject principal to perform the action for the corresponding Subject/user.
 * <p>
 * This principal argument is usually an object representing a user database primary key or a String username or something similar that uniquely identifies an application user. The runtime value of the this principal is application-specific and provided by the application's configured Realms.
 *
 * @see Authorizer
 * @since 1.0
 */
public interface AuthorizationService extends Authorizer {

    /**
     * Returns the (application-unique) name assigned to this <code>AuthorizationService</code>.
     * All services configured for a single application must have a unique name.
     *
     * @return the (application-unique) name assigned to this <code>AuthorizationService</code>.
     *
     * @since 1.0
     */
    String getName();
}
