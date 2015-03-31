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
package org.panifex.web.spi.security;

public interface LoginViewModel {

    // attributes
    String USERNAME_ATTR = "username";
    String getUsername();
    void setUsername(String username);

    String PASSWORD_ATTR = "password";
    String getPassword();
    void setPassword(String password);

    String IS_REMEMBER_ME_ATTR = "isRememberMe";
    boolean getIsRememberMe();
    void setIsRememberMe(boolean isRememberMe);

    String LOG_IN_COMMAND = "logIn";
    void logIn();

    /**
     * Resets username and password fields.
     */
    String RESET_COMMAND = "reset";
    void reset();
}
