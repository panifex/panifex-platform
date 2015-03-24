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

import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.module.api.pagelet.GenericPagelet;
import org.panifex.module.api.pagelet.Pagelet;

/**
 * Base login pagelet template that can be used for implementing
 * concrete pagelet.
 */
public abstract class LoginPagelet<Request>
        extends GenericPagelet<Request>
        implements Pagelet<Request> {

    // text fields
    public static final String USERNAME_TXT_ID = "username-txt";
    public static final String PASSWORD_TXT_ID = "password-txt";

    // buttons
    public static final String LOGIN_BUTTON_ID = "login-btn";
    public static final String RESET_BUTTON_ID = "reset-btn";

    public LoginPagelet(BlueprintContainer container) {
        super(container);
    }

}
