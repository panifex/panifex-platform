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

import org.panifex.web.impl.view.layout.LayoutRichlet;
import org.panifex.web.impl.view.layout.LayoutVM;
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
public class LoginFormRichlet extends LayoutRichlet {

    private Logger log = LoggerFactory.getLogger(LoginFormRichlet.class);

    public static final String URL = "/zk/login";
    
    // commands
    private static final String SIGN_IN_COMMAND = "'" + LoginFormVM.SIGN_IN_COMMAND + "'";
    
    // properties
    private static final String USERNAME_PROPERTY = VM_BIND_ID + "." + LoginFormVM.USERNAME_PROPERTY;
    private static final String PASSWORD_PROPERTY = VM_BIND_ID + "." + LoginFormVM.PASSWORD_PROPERTY;
    private static final String IS_REMEMBER_ME_PROPERTY = VM_BIND_ID + "." + LoginFormVM.IS_REMEMBER_ME_PROPERTY;
    
    @Override
    protected Component createContent() {
        log.debug("Create content");

        Div content = new Div();
        content.setSclass("account-container");

        // header area
        createContentHeaderArea(content);

        // fields area
        createContentFieldsArea(content);

        // actions area
        createContentActionsArea(content);

        return content;
    }

    private void createContentHeaderArea(Div content) {
        // create header area
        H1 h1 = new H1();
        content.appendChild(h1);

        Text h1Label = new Text(getLabel("login.form.header.title1"));
        h1.appendChild(h1Label);
    }
    
    private void createContentFieldsArea(Div content) {
        // create field area
        Div fieldsArea = new Div();
        fieldsArea.setSclass("login-fields");
        content.appendChild(fieldsArea);

        // header field area
        createFieldAreaHeader(fieldsArea);

        // username field area
        createUsernameFieldArea(fieldsArea);

        // password field area
        createPasswordFieldArea(fieldsArea);
    }

    private void createFieldAreaHeader(Div fieldsArea) {
        // create field area header
        P p = new P();
        fieldsArea.appendChild(p);

        Text pLabel = new Text(getLabel("login.form.header.title2"));
        p.appendChild(pLabel);
    }

    private void createUsernameFieldArea(Div fieldsArea) {
        // create username field area
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);

        // Create username textbox
        Textbox textbox = new Textbox();
        textbox.setId("username");
        textbox.setSclass("username-field");
        textbox.setPlaceholder(getLabel("login.form.field.username.placeholder"));
        textbox.setTooltip(getLabel("login.form.field.username.tooltip"));
        fieldArea.appendChild(textbox);
        getBinder().addPropertyLoadBindings(textbox, "value", USERNAME_PROPERTY, null, null,
                null, null, null);
        getBinder().addPropertySaveBindings(textbox, "value", USERNAME_PROPERTY, null, null,
                null, null, null, null, null);
    }
    
    private void createPasswordFieldArea(Div fieldsArea) {
        // create password field area
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);

        // Create password textbox
        Textbox textbox = new Textbox();
        textbox.setId("password");
        textbox.setType("password");
        textbox.setSclass("password-field");
        textbox.setPlaceholder(getLabel("login.form.field.password.placeholder"));
        textbox.setTooltip(getLabel("login.form.field.password.tooltip"));
        fieldArea.appendChild(textbox);
        getBinder().addPropertyLoadBindings(textbox, "value", PASSWORD_PROPERTY, null, null,
                null, null, null);
        getBinder().addPropertySaveBindings(textbox, "value", PASSWORD_PROPERTY, null, null,
                null, null, null, null, null);
    }
    
    private void createContentActionsArea(Div content) {
        // create actions area
        Div actionsArea = new Div();
        actionsArea.setSclass("pull-left");
        content.appendChild(actionsArea);

        // create remember me checkbox
        createRemembermeCheckbox(actionsArea);

        // Creates sign in button
        createSignInButton(actionsArea);
    }
    
    private void createRemembermeCheckbox(Div actionsArea) {
        Checkbox checkbox =
                new Checkbox(getLabel("login.form.field.rememberme.label"));
        checkbox.setSclass("field login-checkbox");
        checkbox.setTooltip(getLabel("login.form.field.rememberme.tooltip"));
        actionsArea.appendChild(checkbox);
        getBinder().addPropertyLoadBindings(checkbox, "checked", IS_REMEMBER_ME_PROPERTY, null,
                null, null, null, null);
        getBinder().addPropertySaveBindings(checkbox, "checked", IS_REMEMBER_ME_PROPERTY, null,
                null, null, null, null, null, null);
    }
    
    private void createSignInButton(Div actionsArea) {
        // create sign in button
        Button button = new Button(getLabel("login.form.button.login.label"));
        button.setClass("button btn btn-primary btn-large");
        actionsArea.appendChild(button);
        button.setType("submit");
        getBinder().addCommandBinding(button, Events.ON_CLICK, SIGN_IN_COMMAND, null);
    }
    
    @Override
    protected LayoutVM getViewModel() {
        LoginFormController controller = new LoginFormControllerImpl();
        LoginFormVM vm = new LoginFormVM(controller);
        return vm;
    }

}
