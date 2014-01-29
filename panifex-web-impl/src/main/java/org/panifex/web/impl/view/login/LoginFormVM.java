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

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.web.impl.view.layout.LayoutVM;
import org.panifex.web.impl.view.security.SecurityLabels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeNode;

public final class LoginFormVM extends LayoutVM {

    private Logger log = LoggerFactory.getLogger(LoginFormVM.class);

    public static final String ID = "org.panifex.web.impl.view.login.LoginFormVM";
    public static final String USERNAME_PARAM = "org.panifex.web.impl.view.login.LoginFormVM.USERNAME_PARAM";
    
    // commands
    public static final String SIGN_IN_COMMAND = "signIn";
    
    // properties
    public static final String USERNAME_ATTR = "username";
    public static final String PASSWORD_ATTR = "password";
    public static final String IS_REMEMBER_ME_ATTR = "isRememberMe";
    
    private String username;
    private String password;
    private boolean isRememberMe = true;

    private final LoginFormController controller;
    
    public LoginFormVM(LoginFormController controller) {
        this.controller = controller;
    }
    
    /**
     * Initializes the {@link LoginFormVM} instance.
     * <p>
     * It tries to get the username parameter from the current {@link org.zkoss.zk.ui.Session Session} 
     * object. If the parameter is set in the current {@link org.zkoss.zk.ui.Session Session},
     * then it will be set to the username field.
     */
    @Init
    public void init() {
        // get current session
        Session currentSession = Sessions.getCurrent();
        
        // get parameters from current session and remove them
        @SuppressWarnings("unchecked")
        final Map<String, Object> params = (Map<String, Object>) 
                currentSession.removeAttribute(ID);
        
        if (params != null) {
            // get the username parameter if the parameters is not null
            username = (String) params.get(USERNAME_PARAM);
        }
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsRememberMe() {
        return isRememberMe;
    }

    public void setIsRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }

    @Command(SIGN_IN_COMMAND)
    public void signIn() {
        log.debug("User is signing in");

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(isRememberMe);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            // perform login
            currentUser.login(token);
            
            // the user has successfully signed in
            log.info("User {} has been signed in", username);
            controller.onSuccessfulLoginIn();
            
        } catch (ExpiredCredentialsException e) {
            controller.onExpiredCredentialsException(username);
        } catch (UnknownAccountException e) {
            Messagebox.show(Labels.getLabel(SecurityLabels.ON_UNKNOWN_ACCOUNT_EXCEPTION_MESSAGE), null, 0,
                    Messagebox.ERROR);
        } catch (IncorrectCredentialsException e) {
            Messagebox.show(Labels.getLabel(SecurityLabels.ON_INCORRECT_CREDENTIALS_EXCEPTION_MESSAGE), null, 0,
                    Messagebox.ERROR);
        } catch (ExcessiveAttemptsException e) {
            Messagebox.show("", null, 0, Messagebox.ERROR);
        } catch (LockedAccountException e) {} catch (DisabledAccountException e) {

        } catch (ConcurrentAccessException e) {} catch (AccountException e) {

        } catch (AuthenticationException e) {
            Messagebox.show("An error during the Authentication process");
        }
    }

    @Override
    public List<TreeNode<MenuItem>> getMenuItems() {
        return null;
    } 
    
}
