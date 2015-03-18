/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.web.vaadin.security.login;

import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.module.vaadin.api.GenericVaadinPagelet;
import org.panifex.web.spi.security.LoginViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginVaadinPagelet extends GenericVaadinPagelet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public LoginVaadinPagelet(BlueprintContainer blueprintContainer) {
        super(blueprintContainer);
    }

    @Override
    public String getName() {
        return "LoginVaadinPagelet";
    }

    @Override
    public void service(VaadinRequest request) throws Exception {
        log.debug("Create login vaadin pagelet content");

        LoginViewModel bean = getComponentInstance(LoginViewModel.class);
        BeanItem<LoginViewModel> viewModel = new BeanItem<>(bean);

        VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("account-container");

        createContentHeaderArea(layout);
        createContentFieldsArea(layout, viewModel);
        createContentActionsArea(layout, viewModel);

        UI.getCurrent().setContent(layout);
    }

    private void createContentHeaderArea(Layout layout) {
        // create header area
        Label h1Label = new Label();
        h1Label.setStyleName("h1");
        layout.addComponent(h1Label);
    }

    private void createContentFieldsArea(Layout layout, BeanItem<LoginViewModel> viewModel) {
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.setStyleName("login-fields");

        createFieldHeaderArea(fieldsLayout);
        createUsernameFieldArea(fieldsLayout, viewModel);
        createPasswordFieldArea(fieldsLayout, viewModel);

        layout.addComponent(fieldsLayout);
    }

    private void createFieldHeaderArea(Layout layout) {

    }

    private void createUsernameFieldArea(Layout layout, BeanItem<LoginViewModel> viewModel) {
        HorizontalLayout fieldArea = new HorizontalLayout();
        fieldArea.setStyleName("field-area");
        layout.addComponent(fieldArea);

        // create username text field
        TextField usernameTextField = new TextField(
                viewModel.getItemProperty(LoginViewModel.USERNAME_ATTR));
        usernameTextField.setId("username-txt");
        usernameTextField.setStyleName("username-field");
        fieldArea.addComponent(usernameTextField);
    }

    private void createPasswordFieldArea(Layout layout, BeanItem<LoginViewModel> viewModel) {
        HorizontalLayout fieldArea = new HorizontalLayout();
        fieldArea.setStyleName("field-area");
        layout.addComponent(fieldArea);

        // create password text field
        TextField passwordTextField = new TextField(
                viewModel.getItemProperty(LoginViewModel.PASSWORD_ATTR));
        passwordTextField.setId("password-txt");
        passwordTextField.setStyleName("password-field");
        fieldArea.addComponent(passwordTextField);
    }

    private void createContentActionsArea(Layout layout, BeanItem<LoginViewModel> viewModel) {
        // create actions area
        HorizontalLayout actionsArea = new HorizontalLayout();
        layout.addComponent(actionsArea);

        createResetButton(actionsArea, viewModel);
    }

    private void createResetButton(Layout layout, final BeanItem<LoginViewModel> viewModel) {
        Button button = new Button();
        button.setId("reset-btn");
        layout.addComponent(button);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewModel.getBean().reset();
            }
        });
    }
}