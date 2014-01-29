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

import org.panifex.web.impl.view.security.SecurityLabels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;

/**
 * This class checks if the entered new password and the entered repeated new
 * password equal.
 *
 */
public final class ChangePasswordFormValidator extends AbstractValidator {

    private final Logger log = LoggerFactory.getLogger(ChangePasswordFormValidator.class);
    
    public static final String PASSWORDS_NOT_EQUAL = "passwordsNotEqual";
    
    /**
     * Checks if the entered new password and the entered repeate new password
     * equal.
     */
    @Override
    public void validate(ValidationContext ctx) {
        log.debug("Validate if the passwords are equal.");
        
        // get the new password
        String newPassword = (String) 
                ctx.getProperties(ChangePasswordFormVM.NEW_PASSWORD_ATTR)[0].getValue();
        
        // get the repeated new password
        String repeatNewPassword = (String)
                ctx.getProperties(ChangePasswordFormVM.REPEAT_NEW_PASSWORD_ATTR)[0].getValue();
        
        assertEquals(ctx, newPassword, repeatNewPassword);
    }
    
    /**
     * Checks if the passwords are equals.
     * 
     * @param ctx the {@link org.zkoss.bind.ValidationContext}
     * @param newPassword the new password
     * @param repeatNewPassword the repeated new password
     */
    private void assertEquals(ValidationContext ctx, String newPassword, String repeatNewPassword) {
        // entered passwords must not be null to be validated
        if (newPassword != null && repeatNewPassword != null) {
            // check if it is not equal
            if (!newPassword.equals(repeatNewPassword)) {
                log.debug("Passwords are not equal.");
                
                // passwords are not equal, add an invalid message
                addInvalidMessage(ctx, 
                    PASSWORDS_NOT_EQUAL, 
                    Labels.getLabel(SecurityLabels.ON_PASSWORDS_NOT_MATCH_TITLE));
            }
        }   
    }

}
