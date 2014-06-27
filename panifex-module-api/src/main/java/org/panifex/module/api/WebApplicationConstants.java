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
     */
    public static final String DEFAULT_LOGIN_URL = "/login";

    /**
     * Default success url to use as the default location a user is sent after logging in. Typically a redirect
     * after login will redirect to the originally request URL; this property is provided mainly as a fallback in case
     * the original request URL is not available or not specified.
     */
    public static final String DEFAULT_SUCCESS_URL = "/main";

    /**
     * Simple default welcome URL equal to <code>/welcome</code>
     */
    public static final String DEFAULT_WELCOME_URL = "/index";

    public static final String PROPERTY_LOGIN_URL = "loginUrl";

    public static final String PROPERTY_SUCCESS_URL = "successUrl";

    public static final String PROPERTY_WELCOME_URL = "welcomeUrl";

    /**
     * Private constructors prevents instantiation.
     */
    private WebApplicationConstants() {
    }
}
