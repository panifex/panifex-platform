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

public interface SecurityFilter extends Filter {

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

    /**
     * Returns a HTTP login request parameter which contains an user's password. The
     * parameter is used in login process.
     *
     * @return the HTTP parameter which contains an user's password
     */
    String getPasswordParam();

    /**
     * Sets a HTTP login request parameter which contains an user's password. The
     * parameter is used in login process.
     *
     * @param passwordParam the HTTP parameter which contains an user's password
     */
    void setPasswordParam(String passwordParam);

    /**
     * Returns a HTTP login request parameter which contains a <i>remember me</i>
     * property. The parameter is used in login process.
     *
     * @return the HTTP parameter which contains a <i>remember me</i> property
     */
    String getRememberMeParam();

    /**
     * Sets a HTTP login request parameter which contains a <i>remember me</i> property.
     * The parameter is used in login process.
     *
     * @param rememberMeParam
     *      the HTTP parameter which contains a <i>remember me</i> property
     */
    void setRememberMeParam(String rememberMeParam);

    /**
     * Returns the success url to use as the default location a user is sent after logging in. Typically a redirect
     * after login will redirect to the originally request URL; this property is provided mainly as a fallback in case
     * the original request URL is not available or not specified.
     * <p/>
     *
     * @return the success url to use as the default location a user is sent after logging in.
     */
    String getSuccessUrl();

    /**
     * Sets the default/fallback success url to use as the default location a user is sent after logging in.  Typically
     * a redirect after login will redirect to the originally request URL; this property is provided mainly as a
     * fallback in case the original request URL is not available or not specified.
     * <p/>
     *
     * @param successUrl the success URL to redirect the user to after a successful login.
     */
    void setSuccessUrl(String successUrl);

    /**
     * Returns the HTTP login request parameter which contains an username. The parameter
     * is used in login process.
     *
     * @return the HTTP parameter which contains an username
     */
    String getUsernameParam();

    /**
     * Sets the HTTP login request parameter which contains an username. The parameter
     * is used in login process.
     *
     * @param usernameParam the HTTP parameter which contains an username
     */
    void setUsernameParam(String usernameParam);
}
