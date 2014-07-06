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
package org.panifex.web.zk.impl.login;

import org.panifex.web.zk.impl.LayoutZkPagelet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.zhtml.H1;
import org.zkoss.zhtml.P;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

public class LoginZkPagelet extends LayoutZkPagelet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String USERNAME_PROPERTY = vm(LoginViewModel.USERNAME_ATTR);
    private final String PASSWORD_PROPERTY = vm(LoginViewModel.PASSWORD_ATTR);
    private final String IS_REMEMBER_ME_PROPERTY = vm(LoginViewModel.IS_REMEMBER_ME_ATTR);

    @Override
    protected void createContent(Component parent) {
        log.debug("Create login zk pagelet content");

        LoginViewModel viewModel = getComponentInstance(LoginViewModel.class);

        DefaultBinder binder = createBinder(parent, viewModel);

        Div content = new Div();
        content.setSclass("account-container");
        parent.appendChild(content);

        createContentHeaderArea(content);
        createContentFieldsArea(content, binder);
        createContentActionsArea(content, binder);

        binder.loadComponent(parent, true);
    }

    private void createContentHeaderArea(Div content) {
        // create header area
        H1 h1 = new H1();
        content.appendChild(h1);

        //Text h1Label = new Text(Labels.getLabel(SecurityLabels.LOGIN_FORM_TITLE));
        //h1.appendChild(h1Label);
    }

    private void createContentFieldsArea(Div content, Binder binder) {
        // create field area
        Div fieldsArea = new Div();
        fieldsArea.setSclass("login-fields");
        content.appendChild(fieldsArea);

        // header field area
        createFieldAreaHeader(fieldsArea);

        // username field area
        createUsernameFieldArea(fieldsArea, binder);

        // password field area
        createPasswordFieldArea(fieldsArea, binder);
    }

    private void createFieldAreaHeader(Div fieldsArea) {
        // create field area header
        P p = new P();
        fieldsArea.appendChild(p);

        //Text pLabel = new Text(Labels.getLabel(SecurityLabels.LOGIN_FORM_HEADER));
        //p.appendChild(pLabel);
    }

    private void createUsernameFieldArea(Div fieldsArea, Binder binder) {
        // create username field area
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);

        // Create username textbox
        Textbox textbox = new Textbox();
        textbox.setId("username");
        textbox.setSclass("username-field");
        //textbox.setPlaceholder(Labels.getLabel(SecurityLabels.ACCOUNT_USERNAME_PLACEHOLDER));
        fieldArea.appendChild(textbox);
        binder.addPropertyLoadBindings(textbox, "value", USERNAME_PROPERTY, null, null,
                null, null, null);
        binder.addPropertySaveBindings(textbox, "value", USERNAME_PROPERTY, null, null,
                null, null, null, null, null);
    }

    private void createPasswordFieldArea(Div fieldsArea, Binder binder) {
        // create password field area
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);

        // Create password textbox
        Textbox textbox = new Textbox();
        textbox.setId("password");
        textbox.setType("password");
        textbox.setSclass("password-field");
//        textbox.setPlaceholder(Labels.getLabel(SecurityLabels.ACCOUNT_PASSWORD_PLACEHOLDER));
        fieldArea.appendChild(textbox);
        binder.addPropertyLoadBindings(textbox, "value", PASSWORD_PROPERTY, null, null,
                null, null, null);
        binder.addPropertySaveBindings(textbox, "value", PASSWORD_PROPERTY, null, null,
                null, null, null, null, null);
    }

    private void createContentActionsArea(Div content, Binder binder) {
        // create actions area
        Div actionsArea = new Div();
        actionsArea.setSclass("pull-left");
        content.appendChild(actionsArea);

        // create remember me checkbox
        createRemembermeCheckbox(actionsArea, binder);

        // Creates sign in button
        createSignInButton(actionsArea, binder);
    }

    private void createRemembermeCheckbox(Div actionsArea, Binder binder) {
        Checkbox checkbox = new Checkbox();
//                new Checkbox(Labels.getLabel(SecurityLabels.REMEMBER_ME_TITLE));
        checkbox.setSclass("field login-checkbox");
        actionsArea.appendChild(checkbox);
        binder.addPropertyLoadBindings(checkbox, "checked", IS_REMEMBER_ME_PROPERTY, null,
                null, null, null, null);
        binder.addPropertySaveBindings(checkbox, "checked", IS_REMEMBER_ME_PROPERTY, null,
                null, null, null, null, null, null);
    }

    private void createSignInButton(Div actionsArea, Binder binder) {
        // create sign in button
        //Button button = new Button(Labels.getLabel(SecurityLabels.LOGIN_ACTION_LABEL));
        Button button = new Button();
        button.setClass("button btn btn-primary btn-large");
        actionsArea.appendChild(button);
        button.setType("submit");
        binder.addCommandBinding(button, Events.ON_CLICK, LoginViewModel.SIGN_IN_COMMAND, null);
    }
}