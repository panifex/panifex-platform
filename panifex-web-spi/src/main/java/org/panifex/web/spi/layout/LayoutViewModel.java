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
package org.panifex.web.spi.layout;

/**
 * A base layout view-model interface that contains fields that can
 * be used in wide-range view-models.
 */
public interface LayoutViewModel {

    // attributes
    String IS_USER_AUTHENTICATED = "isUserAuthenticated";
    String IS_USER_LOGGED = "isUserLogged";

    /**
     * Returns true if the current subject is authenticated.
     *
     * @see <a href="http://shiro.apache.org/authentication.html">http://shiro.apache.org/authentication.html</a>
     */
    boolean getIsUserAuthenticated();

    /**
     * Returns true if the current subject is remembered or authenticated.
     *
     * @see <a href="http://shiro.apache.org/authentication.html">http://shiro.apache.org/authentication.html</a>
     */
    boolean getIsUserLogged();
}
