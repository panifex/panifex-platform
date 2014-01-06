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

import org.panifex.web.impl.event.RedirectToLoginFormEventListener;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

public class ChangePasswordFormControllerImpl implements ChangePasswordFormController {

    /**
     * Shows the information message box and then redirects to the login form.
     */
    @Override
    public void onSuccessfullyChangePassword() {
        Messagebox.show(
            Labels.getLabel("changepassword.form.success.message"),
            Labels.getLabel("changepassword.form.success.title"),
            Messagebox.OK, 
            Messagebox.INFORMATION,
            new RedirectToLoginFormEventListener());
    }
    
    /**
     * Shows the 'sorry, something went wrong' message box and then redirects
     * the user to the login form.
     */
    @Override
    public void onWrongParametersPassed() {
        Messagebox.show(
            Labels.getLabel("exception.messagebox.generic.message"),
            Labels.getLabel("exception.messagebox.generic.title"),
            Messagebox.OK, 
            Messagebox.ERROR,
            new RedirectToLoginFormEventListener());
    }

    /**
     * Shows the message box which informs the user that the account
     * does not exist and then redirects the user to the login form.
     */
    @Override
    public void onUnknownAccountException() {
        Messagebox.show(
            Labels.getLabel("changepassword.form.fault.UnknownAccountException"),
            Labels.getLabel("changepassword.form.title"),
            Messagebox.OK, 
            Messagebox.EXCLAMATION,
            new RedirectToLoginFormEventListener());
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
            Labels.getLabel("changepassword.form.fault.IncorrectCredentialsException"),
            Labels.getLabel("changepassword.form.title"),
            Messagebox.OK,
            Messagebox.EXCLAMATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAccountNotExpiredException() {
        Messagebox.show(
            Labels.getLabel("changepassword.form.fault.AccountNotExpired"),
            Labels.getLabel("changepassword.form.title"),
            Messagebox.OK, 
            Messagebox.EXCLAMATION,
            new RedirectToLoginFormEventListener());
    }
}
