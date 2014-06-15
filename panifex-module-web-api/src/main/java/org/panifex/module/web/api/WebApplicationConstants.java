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
package org.panifex.module.web.api;

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

    public static final String PROPERTY_LOGIN_URL = "loginUrl";

    /**
     * Private constructors prevents instantiation.
     */
    private WebApplicationConstants() {
    }
}
