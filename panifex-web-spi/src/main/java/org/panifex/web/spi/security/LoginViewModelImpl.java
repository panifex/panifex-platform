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

import org.apache.commons.lang3.StringUtils;
import org.panifex.web.spi.security.LoginViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginViewModelImpl implements LoginViewModel {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String username = StringUtils.EMPTY;
    private String password = StringUtils.EMPTY;
    private boolean isRememberMe = false;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean getIsRememberMe() {
        return isRememberMe;
    }

    @Override
    public void setIsRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }

    @Override
    public void signIn() {
        log.info("Sign in {} user", username);
    }

    @Override
    public void reset() {
        log.info("Reset login view model");
        username = StringUtils.EMPTY;
        password = StringUtils.EMPTY;
    }
}
