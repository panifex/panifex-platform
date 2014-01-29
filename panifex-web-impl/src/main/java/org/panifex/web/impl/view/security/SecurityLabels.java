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
package org.panifex.web.impl.view.security;

public final class SecurityLabels {

    public static final String ACCOUNT_NEWPASSWORD_PLACEHOLDER = "security.account.newpassword.placeholder"; // Please enter the new password
    public static final String ACCOUNT_NEWPASSWORD_TITLE = "security.account.newpassword.title"; // New password
    public static final String ACCOUNT_PASSWORD_PLACEHOLDER = "security.account.password.placeholder"; // Please enter your password
    public static final String ACCOUNT_PASSWORD_TITLE = "security.account.password.title"; // Password
    public static final String ACCOUNT_OLDPASSWORD_PLACEHOLDER = "security.account.oldpassword.placeholder"; // Please enter the old password
    public static final String ACCOUNT_OLDPASSWORD_TITLE = "security.account.oldpassword.title"; // Old password
    public static final String ACCOUNT_REPEATPASSWORD_PLACEHOLDER = "security.account.repeatpassword.placeholder"; // Please repeat the new password
    public static final String ACCOUNT_REPEATPASSWORD_TITLE = "security.account.repeatpassword.title"; // Repeat new password
    public static final String ACCOUNT_USERNAME_PLACEHOLDER = "security.account.username.placeholder"; // Please enter your username
    public static final String ACCOUNT_USERNAME_TITLE = "security.account.username.title"; // Username
    
    public static final String CHANGEPASSWORD_ACTION_LABEL = "security.changepassword.action.label"; // Change Password
    public static final String CHANGEPASSWORD_FORM_HEADER1 = "security.changepassword.form.header1"; // Your current password has expired. Please change it before proceeding.
    public static final String CHANGEPASSWORD_FORM_HEADER2 = "security.changepassword.form.header2"; // Your new password must be different from your old password, and at least 6 characters in length.
    public static final String CHANGEPASSWORD_FORM_TITLE = "security.changepassword.form.title"; // Change Your Password
    public static final String CHANGEPASSWORD_SUBMIT_TITLE = "security.changepassword.submit.title"; // Submit new password
    public static final String CHANGEPASSWORD_SUCCESS_MESSAGE = "security.changepassword.success.message"; // The password has been changed
    public static final String CHANGEPASSWORD_SUCCESS_TITLE = "security.changepassword.success.title"; // Password changed
    
    public static final String LOGIN_ACTION_LABEL = "security.login.action.label"; // Sign In
    public static final String LOGIN_FORM_TITLE = "security.login.form.title"; // Sign In
    public static final String LOGIN_FORM_HEADER = "security.login.form.header"; // Sign in using your registered account
    
    public static final String LOGOUT_ACTION_LABEL = "security.logout.action.label"; // Logout
    
    public static final String ON_ACCOUNT_NOT_EXPIRED_EXCEPTION_MESSAGE = "security.on.account.not.expired.exception.message"; // The account has not been expired any more. Please try to log in again.
    public static final String ON_ACCOUNT_NOT_EXPIRED_EXCEPTION_TITLE = "security.on.account.not.expired.exception.title"; // Not-expired account
    public static final String ON_EXPIRED_CREDENTIALS_EXCEPTION_MESSAGE = "security.on.expired.credentials.exception.message"; // Your credentials has expired. Please change it before proceeding.
    public static final String ON_EXPIRED_CREDENTIALS_EXCEPTION_TITLE = "security.on.expired.credentials.exception.title"; // Expired Credentials
    public static final String ON_INCORRECT_CREDENTIALS_EXCEPTION_MESSAGE = "security.on.incorrect.credentials.message"; // Your password is incorrect. Please try again.
    public static final String ON_INCORRECT_CREDENTIALS_EXCEPTION_TITLE = "security.on.incorrect.credentials.title"; // Wrong password
    public static final String ON_PASSWORDS_NOT_MATCH_TITLE = "security.on.passwords.not.match.title"; // Passwords not match!
    public static final String ON_UNKNOWN_ACCOUNT_EXCEPTION_MESSAGE = "security.on.unknown.account.message"; // Your user name or password is incorrect.
    public static final String ON_UNKNOWN_ACCOUNT_EXCEPTION_TITLE = "security.on.unknown.account.title"; // Unknown account
    
    public static final String REMEMBER_ME_TITLE = "security.remember.me.title"; // Keep me signed in

    public static final String ROLE_DESCRIPTION_TITLE = "security.role.description.title"; // Description
    public static final String ROLE_NAME_TITLE = "security.role.name.title"; // Name
    public static final String ROLE_VIEW_TITLE = "security.role.view.title"; // Roles

    
    /**
     * Private construct which protects any other class from instantiating.
     */
    private SecurityLabels() {
    }
}
