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
package org.panifex.web.spi.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.panifex.module.api.security.SecurityUtilService;
import org.panifex.module.api.security.SecurityUtilServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginViewModelImpl implements LoginViewModel {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // trackers
    private final SecurityUtilServiceTracker securityUtilServiceTracker;

    private String username = StringUtils.EMPTY;
    private String password = StringUtils.EMPTY;
    private boolean isRememberMe = false;

    public LoginViewModelImpl(
            SecurityUtilServiceTracker securityUtilServiceTracker) {
        this.securityUtilServiceTracker = securityUtilServiceTracker;
    }

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
    public void logIn() {
        log.info("Log in {} user", username);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, isRememberMe);
        SecurityUtilService service = securityUtilServiceTracker.service();
        Subject currentSubject = service.getSubject();
        currentSubject.login(token);
    }

    @Override
    public void reset() {
        log.info("Reset login form");
        username = StringUtils.EMPTY;
        password = StringUtils.EMPTY;
    }
}
