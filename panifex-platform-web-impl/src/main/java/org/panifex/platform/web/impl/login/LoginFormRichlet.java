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
package org.panifex.platform.web.impl.login;

import org.panifex.platform.web.impl.main.AbstractRichlet;
import org.panifex.platform.web.impl.main.AbstractVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zhtml.H1;
import org.zkoss.zhtml.P;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

/**
 * Composes the login form.
 * 
 */
public class LoginFormRichlet extends AbstractRichlet {

    private Logger log = LoggerFactory.getLogger(LoginFormRichlet.class);

    @Override
    protected Component createContent() {
        log.debug("Create content");

        final Div content = new Div();
        content.setSclass("account-container");;

        final H1 h1 = new H1();
        h1.setParent(content);

        final Text h1Label = new Text(getLabel("login.form.header.title1"));
        h1Label.setParent(h1);

        final Div loginFields = new Div();
        loginFields.setSclass("login-fields");
        loginFields.setParent(content);

        final P p = new P();
        p.setParent(loginFields);

        final Text pLabel = new Text(getLabel("login.form.header.title2"));
        pLabel.setParent(p);

        final Div usernameField = new Div();
        usernameField.setSclass("field");
        usernameField.setParent(loginFields);

        // Creates username textbox
        final Textbox usernameTextbox = new Textbox();
        usernameTextbox.setSclass("username-field");
        usernameTextbox.setPlaceholder(getLabel("login.form.field.username.placeholder"));
        usernameTextbox.setPlaceholder(getLabel("login.form.field.username.tooltip"));
        usernameTextbox.setParent(usernameField);
        getBinder().addPropertyLoadBindings(usernameTextbox, "value", "vm.username", null, null,
                null, null, null);
        getBinder().addPropertySaveBindings(usernameTextbox, "value", "vm.username", null, null,
                null, null, null, null, null);

        final Div passwordField = new Div();
        passwordField.setSclass("field");
        passwordField.setParent(loginFields);

        // Creates password textbox
        final Textbox passwordTextbox = new Textbox();
        passwordTextbox.setSclass("password-field");
        passwordTextbox.setPlaceholder(getLabel("login.form.field.passwors.placeholder"));
        passwordTextbox.setTooltip(getLabel("login.form.field.passwors.tooltip"));
        passwordTextbox.setParent(passwordField);
        getBinder().addPropertyLoadBindings(passwordTextbox, "value", "vm.password", null, null,
                null, null, null);
        getBinder().addPropertySaveBindings(passwordTextbox, "value", "vm.password", null, null,
                null, null, null, null, null);

        final Div loginActions = new Div();
        loginActions.setSclass("pull-left");
        loginActions.setParent(content);

        // Creates remember me checkbox
        final Checkbox remembermeCheckbox =
                new Checkbox(getLabel("login.form.field.rememberme.label"));
        remembermeCheckbox.setSclass("field login-checkbox");
        remembermeCheckbox.setTooltip(getLabel("login.form.field.rememberme.tooltip"));
        remembermeCheckbox.setParent(loginActions);
        getBinder().addPropertyLoadBindings(remembermeCheckbox, "checked", "vm.isRememberMe", null,
                null, null, null, null);
        getBinder().addPropertySaveBindings(remembermeCheckbox, "checked", "vm.isRememberMe", null,
                null, null, null, null, null, null);

        // Creates sign in button
        final Button signInButton = new Button(getLabel("login.form.button.login.label"));
        signInButton.setClass("button btn btn-primary btn-large");
        signInButton.setParent(loginActions);
        signInButton.setType("submit");
        getBinder().addCommandBinding(signInButton, Events.ON_CLICK, "'signIn'", null);

        return content;
    }


    @Override
    protected AbstractVM getViewModel() {
        return new LoginFormVM();
    }

}
