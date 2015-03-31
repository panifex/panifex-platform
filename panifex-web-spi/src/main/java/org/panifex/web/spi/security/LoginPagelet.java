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
package org.panifex.web.spi.security;

import org.apache.commons.lang3.Validate;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.module.api.pagelet.GenericPagelet;
import org.panifex.module.api.pagelet.Pagelet;
import org.panifex.web.spi.html.Button;
import org.panifex.web.spi.html.Container;
import org.panifex.web.spi.html.Event;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.TextField;
import org.panifex.web.spi.html.VerticalLayout;
import org.panifex.web.spi.tracker.GuiFactoryTracker;

/**
 * Base login pagelet template that can be used for implementing
 * concrete pagelet.
 */
public abstract class LoginPagelet<Request>
        extends GenericPagelet<Request>
        implements Pagelet<Request> {

    // buttons
    public static final String LOGIN_BUTTON_ID = "login-btn";
    public static final String RESET_BUTTON_ID = "reset-btn";

    // containers
    public static final String BUTTONS_AREA_ID = "buttons-area-id";
    public static final String FIELDS_AREA_ID = "fields-area-id";
    public static final String LOGIN_FORM_CONTAINER_ID = "login-form-cnt-id";
    public static final String PASSWORD_FIELD_AREA_ID = "password-field-area-id";
    public static final String USERNAME_FIELD_AREA_ID = "username-field-area-id";

    // text fields
    public static final String USERNAME_TXT_ID = "username-txt";
    public static final String PASSWORD_TXT_ID = "password-txt";

    private final GuiFactoryTracker guiFactoryTracker;

    // thread locals
    private final ThreadLocal<LoginViewModel> viewModelThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<GuiFactory> guiFactoryThreadLocal = new ThreadLocal<>();

    public LoginPagelet(
            BlueprintContainer blueprintContainer,
            GuiFactoryTracker guiFactoryTracker) {
        super(blueprintContainer);

        this.guiFactoryTracker = Validate.notNull(guiFactoryTracker);
    }

    @Override
    public final void service(Request request) {
        GuiFactory guiFactory = guiFactoryTracker.service();
        guiFactoryThreadLocal.set(guiFactory);

        // create root content
        VerticalLayout vlayout = guiFactory.createVerticalLayout();
        guiFactory.setPageContent(request, vlayout);

        // init bean item
        LoginViewModel viewModel = getComponentInstance(LoginViewModel.class);
        viewModelThreadLocal.set(viewModel);
        guiFactory.initViewModelBinding(viewModel, vlayout);

        // create log in form container
        createLogInFormContainer(vlayout);

        // load props
        guiFactory.loadComponent(viewModel, vlayout);

        // free thread locals
        guiFactoryThreadLocal.remove();
        viewModelThreadLocal.remove();
    }

    private void createLogInFormContainer(Container parent) {
        // create login form container layout
        VerticalLayout vlayout = guiFactoryThreadLocal.get().createVerticalLayout();
        vlayout.setId(LOGIN_FORM_CONTAINER_ID);
        parent.addHtmlComponent(vlayout);

        // create login form content
        createFieldsArea(vlayout);
        createButtonsArea(vlayout);
    }

    private void createFieldsArea(Container parent) {
        // create fields area container
        VerticalLayout vlayout = guiFactoryThreadLocal.get().createVerticalLayout();
        vlayout.setId(FIELDS_AREA_ID);
        parent.addHtmlComponent(vlayout);

        // create fields area content
        createUsernameField(vlayout);
        createPasswordField(vlayout);
    }

    private void createUsernameField(Container parent) {
        // create username field layout
        HorizontalLayout hlayout = guiFactoryThreadLocal.get().createHorizontalLayout();
        hlayout.setId(USERNAME_FIELD_AREA_ID);
        parent.addHtmlComponent(hlayout);

        // create username text field
        TextField textField = guiFactoryThreadLocal.get().createTextField();
        textField.setId(USERNAME_TXT_ID);
        hlayout.addHtmlComponent(textField);
        guiFactoryThreadLocal.get().
            bindProperty(viewModelThreadLocal.get(), LoginViewModel.USERNAME_ATTR, textField);
    }

    private void createPasswordField(Container parent) {
        // create password field layout
        HorizontalLayout hlayout = guiFactoryThreadLocal.get().createHorizontalLayout();
        hlayout.setId(PASSWORD_FIELD_AREA_ID);
        parent.addHtmlComponent(hlayout);

        // create password text field
        TextField textField = guiFactoryThreadLocal.get().createTextField();
        textField.setId(PASSWORD_TXT_ID);
        hlayout.addHtmlComponent(textField);
        guiFactoryThreadLocal.get().
            bindProperty(viewModelThreadLocal.get(), LoginViewModel.PASSWORD_ATTR, textField);
    }

    private void createButtonsArea(Container parent) {
        // create buttons area container
        HorizontalLayout hlayout = guiFactoryThreadLocal.get().createHorizontalLayout();
        hlayout.setId(BUTTONS_AREA_ID);
        parent.addHtmlComponent(hlayout);

        // create buttons area content
        createLogInButton(parent);
        createResetButton(parent);
    }

    private void createLogInButton(Container parent) {
        // create log in button
        Button button = guiFactoryThreadLocal.get().createButton();
        button.setId(LOGIN_BUTTON_ID);
        parent.addHtmlComponent(button);
        guiFactoryThreadLocal.get().bindCommand(
                Event.ON_CLICK,
                viewModelThreadLocal.get(),
                LoginViewModel.LOG_IN_COMMAND,
                button);
    }

    private void createResetButton(Container parent) {
        // create reset button
        Button button = guiFactoryThreadLocal.get().createButton();
        button.setId(RESET_BUTTON_ID);
        parent.addHtmlComponent(button);
        guiFactoryThreadLocal.get().bindCommand(
                Event.ON_CLICK,
                viewModelThreadLocal.get(),
                LoginViewModel.RESET_COMMAND,
                button);
    }
}
