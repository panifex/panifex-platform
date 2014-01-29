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

import org.panifex.module.api.event.RedirectToURIEventListener;
import org.panifex.web.impl.CommonLabels;
import org.panifex.web.impl.event.RedirectToLoginFormEventListenerFactory;
import org.panifex.web.impl.view.security.SecurityLabels;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

public class ChangePasswordFormControllerImpl implements ChangePasswordFormController {

    /**
     * Shows the information message box and then redirects to the login form.
     * <p>
     * The username parameter is going to be passed to the login form through
     * the current {@link org.zkoss.zk.ui.Session Session}.
     * 
     * @param username the account's username for which is the password successfully updated
     */
    @Override
    public void onSuccessfullyChangePassword(String username) {
        // create the event listener which redirects the user to the login form
        RedirectToURIEventListener eventListener = 
                RedirectToLoginFormEventListenerFactory.createDefaultRedirector(username);
        
        // show message box
        Messagebox.show(
            Labels.getLabel(SecurityLabels.CHANGEPASSWORD_SUCCESS_MESSAGE),
            Labels.getLabel(SecurityLabels.CHANGEPASSWORD_SUCCESS_TITLE),
            Messagebox.OK, 
            Messagebox.INFORMATION,
            eventListener);
    }
    
    /**
     * Shows the 'sorry, something went wrong' message box and then redirects
     * the user to the login form.
     */
    @Override
    public void onWrongParametersPassed() {
        Messagebox.show(
            Labels.getLabel(CommonLabels.ON_EXCEPTION_MESSAGE),
            Labels.getLabel(CommonLabels.ON_EXCEPTION_TITLE),
            Messagebox.OK, 
            Messagebox.ERROR,
            RedirectToLoginFormEventListenerFactory.createDefaultRedirector());
    }

    /**
     * Shows the message box which informs the user that the account
     * does not exist and then redirects the user to the login form.
     */
    @Override
    public void onUnknownAccountException() {
        Messagebox.show(
            Labels.getLabel(SecurityLabels.ON_UNKNOWN_ACCOUNT_EXCEPTION_MESSAGE),
            Labels.getLabel(SecurityLabels.ON_UNKNOWN_ACCOUNT_EXCEPTION_TITLE),
            Messagebox.OK, 
            Messagebox.EXCLAMATION,
            RedirectToLoginFormEventListenerFactory.createDefaultRedirector());
    }
    
    /**
     * Shows the message box which informs the user that it provide a password
     * which does not match the current password.
     * <p>
     * It does not redirect the user to the login form.
     */
    
    @Override
    public void onIncorrectCredentialsException() {
        Messagebox.show(
            Labels.getLabel(SecurityLabels.ON_INCORRECT_CREDENTIALS_EXCEPTION_MESSAGE),
            Labels.getLabel(SecurityLabels.ON_INCORRECT_CREDENTIALS_EXCEPTION_TITLE),
            Messagebox.OK,
            Messagebox.EXCLAMATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAccountNotExpiredException() {
        Messagebox.show(
            Labels.getLabel(SecurityLabels.ON_ACCOUNT_NOT_EXPIRED_EXCEPTION_MESSAGE),
            Labels.getLabel(SecurityLabels.ON_ACCOUNT_NOT_EXPIRED_EXCEPTION_TITLE),
            Messagebox.OK, 
            Messagebox.EXCLAMATION,
            RedirectToLoginFormEventListenerFactory.createDefaultRedirector());
    }
}
