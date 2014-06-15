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
package org.panifex.web.shiro;

import javax.servlet.Filter;

import org.apache.shiro.web.mgt.WebSecurityManager;

public interface SecurityFilter extends Filter {

    WebSecurityManager getSecurityManager();

    /**
     * Returns the login URL used to authenticate a user.
     *
     * @return the login URL used to authenticate a user, used when redirecting users if authentication is required.
     */
    String getLoginUrl();

    /**
     * Sets the login URL used to authenticate a user.
     */
    void setLoginUrl(String loginUrl);
}
