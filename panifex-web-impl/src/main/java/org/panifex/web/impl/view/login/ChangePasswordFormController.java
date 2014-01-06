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
 * Controller for the {@link ChangePasswordFromFM} class. It contains
 * actions which can happened during the change password process.
 *
 */
public interface ChangePasswordFormController {
   
    /**
     * It is called after the account's password has successfully been changed.
     * 
     * @param username the account's username for which is the password successfully updated
     */
    void onSuccessfullyChangePassword(String username);

    /**
     * It is called if the parameters has not been correctly passed.
     */
    void onWrongParametersPassed();
    
    /**
     * It is called when the user try to change password of the unknown account.
     * <p>
     * It can occur in case the account is deleted during the change
     * password process. 
     */
    void onUnknownAccountException();
    
    /**
     * It is called when the old password does not match the current account's credentials.
     */
    void onIncorrectCredentialsException();
    
    /**
     * It is called when the user try to change password of the not expired account.
     */
    void onAccountNotExpiredException();
}
