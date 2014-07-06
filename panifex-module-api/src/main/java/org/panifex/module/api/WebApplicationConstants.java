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
package org.panifex.module.api;

/**
 * Web Application related constants.
 */
public class WebApplicationConstants {

    /**
     * Service PID used for configuration.
     */
    public static final String PID = "org.panifex.web";

    /**
     * Simple default login URL equal to <code>/login</code>
     *
     * @see #PROPERTY_LOGIN_URL
     */
    public static final String DEFAULT_LOGIN_URL = "/login";

    /**
     * Default HTTP login request parameter which contains an user's password. The parameter is used
     * in login process.
     *
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * @see #PROPERTY_PASSWORD_PARAM
     */
    public static final String DEFAULT_PASSWORD_PARAM = "password";

    /**
     * Default HTTP login request parameter which contains a <i>remember me</i> property. The
     * parameter is used in login process.
     *
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     */
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";

    /**
     * Default success url to use as the default location a user is sent after logging in. Typically a redirect
     * after login will redirect to the originally request URL; this property is provided mainly as a fallback in case
     * the original request URL is not available or not specified.
     */
    public static final String DEFAULT_SUCCESS_URL = "/main";

    /**
     * Default HTTP login request parameter which contains an username. The parameter is used
     * in login process.
     *
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     */
    public static final String DEFAULT_USERNAME_PARAM = "username";

    /**
     * Simple default welcome URL equal to <code>/welcome</code>
     */
    public static final String DEFAULT_WELCOME_URL = "/index";

    /**
     * Property which can be used to register a new login url.
     *
     * @see #DEFAULT_LOGIN_URL
     */
    public static final String PROPERTY_LOGIN_URL = "loginUrl";

    /**
     * Property which can be used to register a new user's password parameter in HTTP login requests.
     *
     * @see #DEFAULT_PASSWORD_PARAM
     */
    public static final String PROPERTY_PASSWORD_PARAM = "passwordParam";

    /**
     * Property which can be used to register a new <i>remember me</i> parameter in HTTP login
     * requests.
     *
     * @see #DEFAULT_REMEMBER_ME_PARAM
     */
    public static final String PROPERTY_REMEMBER_ME_PARAM = "rememberMeParam";

    /**
     * Property which can be used to register a new username parameter in HTTP login requests.
     *
     * @see #DEFAULT_USERNAME_PARAM
     */
    public static final String PROPERTY_USERNAME_PARAM = "usernameParam";

    /**
     * Property which can be used to register a new success url.
     *
     * @see #DEFAULT_SUCCESS_URL
     */
    public static final String PROPERTY_SUCCESS_URL = "successUrl";

    /**
     * Property which can be used to register a new welcome url.
     */
    public static final String PROPERTY_WELCOME_URL = "welcomeUrl";

    /**
     * Private constructors prevents instantiation.
     */
    private WebApplicationConstants() {
    }
}
