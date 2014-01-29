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
import org.panifex.web.impl.view.security.SecurityLabels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.H1;
import org.zkoss.zhtml.P;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 * Composes the change password form. This form is used in case the account has
 * expired and the user must change its password after it tries to log in.
 *
 */
public class ChangePasswordFormRichlet extends LayoutRichlet {

    private final Logger log = LoggerFactory.getLogger(ChangePasswordFormRichlet.class);
    
    private static final String FORM_ID = "fx";

    // commands
    private static final String CHANGE_PASSWORD_COMMAND = "'" + ChangePasswordFormVM.CHANGE_PASSWORD_COMMAND + "'";
    
    // properties
    private static final String USERNAME_ATTR = FORM_ID + "." + ChangePasswordFormVM.USERNAME_ATTR;
    private static final String OLD_PASSWORD_ATTR = FORM_ID + "." + ChangePasswordFormVM.OLD_PASSWORD_ATTR;
    private static final String NEW_PASSWORD_ATTR = FORM_ID + "." + ChangePasswordFormVM.NEW_PASSWORD_ATTR;
    private static final String REPEAT_NEW_PASSWORD_ATTR = FORM_ID + "." + ChangePasswordFormVM.REPEAT_NEW_PASSWORD_ATTR;
    
    // validators
    private static final String FORM_VALIDATOR = VM_BIND_ID + "." + ChangePasswordFormVM.FORM_VALIDATOR;

    // validation messages
    private static final String PASSWORD_NOT_EQUAL_VSMSG = VALIDATION_MESSAGES + "['" + ChangePasswordFormValidator.PASSWORDS_NOT_EQUAL + "']";
    
    @Override
    protected final void createContent(Component parent) {
        log.debug("Create change password form");
        
        final Div content = new Div();
        content.setSclass("changepass-container");
        parent.appendChild(content);
        
        addFormContentBinding(content);
        
        // header
        createContentHeaderArea(content);
        
        // fields
        createFieldsArea(content);
        
        // actions
        createActionsArea(content);
    }
    
    private void addFormContentBinding(final Div content) {
        // add form init binding
        getBinder().addFormInitBinding(content, FORM_ID, VM_BIND_ID, null);
        
        // add form save binding
        getBinder().addFormSaveBindings(content, FORM_ID, VM_BIND_ID, 
            new String[]{ ChangePasswordFormVM.CHANGE_PASSWORD_COMMAND }, null, null, 
            FORM_VALIDATOR, null);
    }
    
    private void createContentHeaderArea(Div content) {
        H1 h1 = new H1();
        content.appendChild(h1);
        
        final Text h1label = new Text(Labels.getLabel(SecurityLabels.CHANGEPASSWORD_FORM_TITLE));
        h1.appendChild(h1label);
    }

    private void createFieldsArea(Div content) {
        Div fieldsArea = new Div();
        fieldsArea.setSclass("login-fields");
        content.appendChild(fieldsArea);
        
        // header
        createHeaderFieldsArea(fieldsArea);
        
        // username
        createUsernameFieldArea(fieldsArea);
        
        // old password
        createOldPasswordFieldArea(fieldsArea);
        
        // new password
        createNewPasswordFieldArea(fieldsArea);
        
        // repeat new password
        createRepeatNewPasswordFieldArea(fieldsArea);
    }
    
    private void createHeaderFieldsArea(Div fieldsArea) {
        P paragraph1 = new P();
        fieldsArea.appendChild(paragraph1);
        
        Text p1Label = new Text(Labels.getLabel(SecurityLabels.CHANGEPASSWORD_FORM_HEADER1));
        paragraph1.appendChild(p1Label);
        
        P paragraph2 = new P();
        fieldsArea.appendChild(paragraph2);
        
        Text p2Label = new Text(Labels.getLabel(SecurityLabels.CHANGEPASSWORD_FORM_HEADER2));
        paragraph2.appendChild(p2Label);
    }
    
    private void createUsernameFieldArea(Div fieldsArea) {
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);
        
        // Creates the username label
        Label label = new Label(Labels.getLabel(SecurityLabels.ACCOUNT_USERNAME_TITLE));
        label.setSclass("field");
        fieldArea.appendChild(label);
        
        // Creates the username textbox
        Textbox textbox = new Textbox();
        textbox.setId(USERNAME_ATTR);
        textbox.setSclass("username-field");
        textbox.setDisabled(true);
        fieldArea.appendChild(textbox);
        getBinder().addPropertyLoadBindings(textbox, "value", USERNAME_ATTR, null, null,
            null, null, null);
    }
    
    private void createOldPasswordFieldArea(Div fieldsArea) {
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);
        
        // Create the old password label
        Label label = new Label(Labels.getLabel(SecurityLabels.ACCOUNT_OLDPASSWORD_TITLE));
        label.setSclass("field");
        fieldArea.appendChild(label);
        
        // Creates the old password textbox
        Textbox textbox = new Textbox();
        textbox.setId(OLD_PASSWORD_ATTR);
        textbox.setPlaceholder(Labels.getLabel(SecurityLabels.ACCOUNT_OLDPASSWORD_PLACEHOLDER));
        textbox.setSclass("password-field");
        textbox.setType("password");
        fieldArea.appendChild(textbox);
        getBinder().addPropertyLoadBindings(textbox, "value", OLD_PASSWORD_ATTR, null, 
            null, null, null, null);
        getBinder().addPropertySaveBindings(textbox, "value", OLD_PASSWORD_ATTR, null, 
            null, null, null, null, null, null);
    }
    
    private void createNewPasswordFieldArea(Div fieldsArea) {
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);
        
        // Creates the new password label
        Label label = new Label(Labels.getLabel(SecurityLabels.ACCOUNT_NEWPASSWORD_TITLE));
        label.setSclass("field");
        fieldArea.appendChild(label);
        
        // Creates the new password textbox
        Textbox textbox = new Textbox();
        textbox.setId(NEW_PASSWORD_ATTR);
        textbox.setPlaceholder(Labels.getLabel(SecurityLabels.ACCOUNT_NEWPASSWORD_PLACEHOLDER));
        textbox.setSclass("password-field");
        textbox.setType("password");
        fieldArea.appendChild(textbox);
        getBinder().addPropertyLoadBindings(textbox, "value", NEW_PASSWORD_ATTR, null, 
            null, null, null, null);
        getBinder().addPropertySaveBindings(textbox, "value", NEW_PASSWORD_ATTR, null, 
            null, null, null, null, null, null);
    }

    private void createRepeatNewPasswordFieldArea(Div fieldsArea) {
        Div fieldArea = new Div();
        fieldArea.setSclass("field-area");
        fieldsArea.appendChild(fieldArea);
        
        // Creates the repeat new password label
        Label label = new Label(Labels.getLabel(SecurityLabels.ACCOUNT_REPEATPASSWORD_TITLE));
        label.setSclass("field");
        fieldArea.appendChild(label);
        
        // Creates the repeat new password textbox
        Textbox textbox = new Textbox();
        textbox.setId(REPEAT_NEW_PASSWORD_ATTR);
        textbox.setPlaceholder(Labels.getLabel(SecurityLabels.ACCOUNT_REPEATPASSWORD_PLACEHOLDER));
        textbox.setSclass("password-field");
        textbox.setType("password");
        fieldArea.appendChild(textbox);
        getBinder().addPropertyLoadBindings(textbox, "value", REPEAT_NEW_PASSWORD_ATTR, null, 
            null, null, null, null);
        getBinder().addPropertySaveBindings(textbox, "value", REPEAT_NEW_PASSWORD_ATTR, null, 
            null, null, null, null, null, null);
        
        // Creates the error messages label
        final Label errorLabel = new Label();
        errorLabel.setId("repeatNewPasswordErrorLabel");
        errorLabel.setSclass("field");
        fieldArea.appendChild(errorLabel);
        getBinder().addPropertyLoadBindings(errorLabel, "value", 
            PASSWORD_NOT_EQUAL_VSMSG, 
            null, null, null, null, null);
    }

    private void createActionsArea(Div content) {
        Div actionArea = new Div();
        actionArea.setSclass("pull-left");
        actionArea.setParent(content);
        
        // create change button
        createChangePasswordButton(actionArea);
    }
    
    private void createChangePasswordButton(final Div actionArea) {
        final Button changePasswordButton = new Button(Labels.getLabel(SecurityLabels.CHANGEPASSWORD_SUBMIT_TITLE));
        changePasswordButton.setSclass("button btn btn-primary btn-large");
        actionArea.appendChild(changePasswordButton);
        
        // add on click command binding
        getBinder().addCommandBinding(
            changePasswordButton,
            Events.ON_CLICK,
            CHANGE_PASSWORD_COMMAND,
            null);  
    }
    
    @Override
    protected final LayoutVM getViewModel() {
        ChangePasswordFormController controller = new ChangePasswordFormControllerImpl();
        ChangePasswordFormVM vm = new ChangePasswordFormVM(controller);
        return vm;
    }
}
