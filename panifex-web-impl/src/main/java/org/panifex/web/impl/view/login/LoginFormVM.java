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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeNode;

public final class LoginFormVM extends LayoutVM {

    private Logger log = LoggerFactory.getLogger(LoginFormVM.class);

    public static final String ID = "org.panifex.web.impl.view.login.LoginFormVM";
    public static final String USERNAME_PARAM = "org.panifex.web.impl.view.login.LoginFormVM.USERNAME_PARAM";
    
    private String username = "";
    private String password = "";
    private boolean isRememberMe = true;

    private final LoginFormController controller;
    
    public LoginFormVM(LoginFormController controller) {
        this.controller = controller;
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

    public void setIsRememberMe(boolean isRemember) {
        this.isRememberMe = isRemember;
    }

    @Command
    public void signIn() {
        log.debug("User is signing in");

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(isRememberMe);
        Subject currentUser = SecurityUtils.getSubject();

        try {
            // perform login
            currentUser.login(token);
            
            // the user has successfully log in
            log.info("User {} has been logged in", username);
            controller.onSuccessfulLoginIn();
            
        } catch (ExpiredCredentialsException e) {
            controller.onExpiredCredentialsException(username);
        } catch (UnknownAccountException e) {
            Messagebox.show(Labels.getLabel("login.form.fault.UnknownAccountException"), null, 0,
                    Messagebox.ERROR);
        } catch (IncorrectCredentialsException e) {
            Messagebox.show(Labels.getLabel("fault.IncorrectCredentialsException"), null, 0,
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
    protected Logger getLogger() {
        return log;
    }

    @Override
    public List<TreeNode<MenuItem>> getMenuItems() {
        return null;
    } 
    
}
