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

import java.util.Map;

import org.panifex.web.impl.view.layout.LayoutVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * The view-model class for the change password process.
 *
 */
public final class ChangePasswordFormVM extends LayoutVM {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public static final String ID = "org.panifex.web.impl.view.login.ChangePasswordFormVM";
    public static final String USERNAME_PARAM = "org.panifex.web.impl.view.login.ChangePasswordFormVM.USERNAME_PARAM";
    
    // commands
    public static final String CHANGE_PASSWORD_COMMAND = "changePassword";
    
    // attributes
    public static final String USERNAME_ATTR = "username";
    public static final String OLD_PASSWORD_ATTR = "oldPassword";
    public static final String NEW_PASSWORD_ATTR = "newPassword";
    public static final String REPEAT_NEW_PASSWORD_ATTR = "repeatNewPassword";
    
    // validators
    public static final String FORM_VALIDATOR = "formValidator";
    
    private String username;
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;
    
    private final ChangePasswordFormController controller;
    
    /**
     * Contructs a new {@link ChangePasswordFormVM} object.
     * 
     * @param controller implements actions which can happened during the process
     */
    public ChangePasswordFormVM(ChangePasswordFormController controller) {
        this.controller = controller;
    }
    
    /**
     * Initializes the {@link ChangePasswordFormVM} instance.
     * <p>
     * It tries to get the username parameter from the current {@link org.zkoss.zk.ui.Session Session} 
     * object. If the parameter does not exist in the current {@link org.zkoss.zk.ui.Session Session},
     * then calls {@link ChangePasswordFormController#onWrongParametersPassed()} method.
     */
    @Init
    public void init() {
        // get current session
        Session currentSession = Sessions.getCurrent();
        
        // get params from current session and remove it
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) 
                currentSession.removeAttribute(ID);
        
        // check if the parameters is correctly passed
        if (isParamsCorrectlyPassed(map)) {
            // get username param
            username = (String) map.get(USERNAME_PARAM);
        } else {
            // parameters has not been passed, return to login form
            controller.onWrongParametersPassed();
        }
    }

    /**
     * Returns true if the params are correctly passed, or false if it is not.
     * 
     * @param map the {@link java.util.Map} which should contain parameters 
     * @return true if the params are correctly passed, or false if it is not
     */
    private boolean isParamsCorrectlyPassed(final Map<String, Object> map) {
        if (map == null) {
            log.debug("Parameters has not passed");
            return false;
        }
        
        if (map.get(USERNAME_PARAM) == null) {
            // parameters has not been passed, return to login form
            log.debug("The username parameter has not been passed");
            return false;
        }
        
        // parameters is correctly passed. Return true
        return true;
    }

    /**
     * This method is called when the user clicks on the change password button.
     * <p>
     * It calls the {@link org.panifex.service.api.security.SecurityService#updateAccountExpiredPassword(String, String, String)}
     * method which persists a new password.
     * <p>
     * After changing password, it calls the {@link ChangePasswordFormController#onSuccessfullyChangePassword()} method.
     * <p>
     * If something went wrong, it calls the {@link ChangePasswordFormController#onUnknownAccountException()} method,
     * or the {@link ChangePasswordFormController#onAccountNotExpiredException()} method.
     */
    @Command(CHANGE_PASSWORD_COMMAND)
    public void changePassword() {
        /* TODO
        SecurityService securityService = SecurityServiceManager.getService();
        try {
            // update account expired password
            securityService.updateAccountExpiredPassword(username, oldPassword, newPassword);
            
            controller.onSuccessfullyChangePassword(username);
        }  catch (AccountNotExpiredException e) {
            log.debug("The account {} is not expired", username);
            controller.onAccountNotExpiredException();
        } catch (IncorrectCredentialsException e) {
            log.debug("The credentials for account {} are incorrect", username);
            controller.onIncorrectCredentialsException();
        } catch (UnknownAccountException e) {
            log.debug("The account {} has not found.", username);
            controller.onUnknownAccountException();
        } finally {
            // resets password after performing the change password action
            resetPasswords();
        }*/
    }
    
    /**
     * Resets inputed passwords and notifies view about the reseted passwords.
     */
    /*
    private void resetPasswords() {
        // reset the old password
        oldPassword = null;
        BindUtils.postNotifyChange(null, null, this, OLD_PASSWORD_ATTR);
        
        // reset the new password
        newPassword = null;
        BindUtils.postNotifyChange(null, null, this, NEW_PASSWORD_ATTR);
        
        // reset the repeated new password
        repeatNewPassword = null;
        BindUtils.postNotifyChange(null, null, this, REPEAT_NEW_PASSWORD_ATTR);
    }*/
    
    /**
     * Returns the account's username.
     * 
     * @return the account's username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns the account's current old password.
     * 
     * @return the account's current old password.
     */
    public String getOldPassword() {
        return oldPassword;
    }
    
    /**
     * Sets the account's current old password.
     * 
     * @param oldPassword the account's current old password.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    /**
     * Returns the account's new password.
     * 
     * @return the account's new password
     */
    public String getNewPassword() {
        return newPassword;
    }
    
    /**
     * Sets the account's new password.
     * 
     * @param newPassword the account's new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    /**
     * Gets the account's repeated new password.
     * 
     * @return the account's repeated new password
     */
    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }
    
    /**
     * Sets the account's new password.
     * 
     * @param repeatNewPassword the account's repeated new password
     */
    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }
    
    /**
     * Returns the form validator.
     * 
     * @return the form validator
     */
    public Validator getFormValidator() {
        return new ChangePasswordFormValidator();
    }
}
