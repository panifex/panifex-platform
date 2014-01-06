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
package org.panifex.web.impl.view.login;

/**
 * Controller for the {@link LoginFormVM} class. It contains
 * actions which can happened during the signing in process.
 *
 */
public interface LoginFormController {

    /**
     * It is called after the user has successfully been logged in.
     */
    void onSuccessfulLoginIn();
    
    /**
     * It is called when the user try to log in with the expired account.
     * 
     * @param username the expired account's username
     */
    void onExpiredCredentialsException(String username);
}
