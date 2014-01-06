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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;

/**
 * Unit tests for the {@link ChangePasswordFormValidator} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ChangePasswordFormValidator.class)
public final class ChangePasswordFormValidatorTest extends TestSupport {

    /**
     * Tested class.
     */
    private ChangePasswordFormValidator validator;
    
    @Before
    public void setUp() throws Exception {
        validator = createPartialMockAndInvokeDefaultConstructor(ChangePasswordFormValidator.class, "addInvalidMessage");
    }
    
    /**
     * This test checks the {@link ChangePasswordFormValidator#validate(ValidationContext)} method in case
     * the new password and the repeated new password are equal.
     * <p>
     * The error must not be registered because the passwords are equal.
     */
    @Test
    public void passwordsEqualTest() {
        // variables
        String newPassword = getRandomChars(20);
        String repeatNewPassword = newPassword;
        
        // mocks
        ValidationContext ctxMock = createMock(ValidationContext.class);
        Property newPasswordProperty = createMock(Property.class);
        Property repeatNewPasswordProperty = createMock(Property.class);
        
        // expect getting new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ newPasswordProperty });
        expect(newPasswordProperty.getValue()).andReturn(newPassword);
        
        // expect getting repeated new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.REPEAT_NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ repeatNewPasswordProperty});
        expect(repeatNewPasswordProperty.getValue()).andReturn(repeatNewPassword);
        
        replayAll();
        
        validator.validate(ctxMock);
        
        verifyAll();
    }
    
    /**
     * This test checks the {@link ChangePasswordFormValidator#validate(ValidationContext)} method in case
     * the new password and the repeated new password are not equal.
     * <p>
     * The error must be registered because the passwords are not equal.
     */
    @Test
    public void passwordNotEqualTest() throws Exception {
        // variables
        String newPassword = getRandomChars(20);
        String repeatNewPassword = getRandomChars(21);
        
        // mocks
        ValidationContext ctxMock = createMock(ValidationContext.class);
        Property newPasswordProperty = createMock(Property.class);
        Property repeatNewPasswordProperty = createMock(Property.class);
        
        // expect getting new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ newPasswordProperty });
        expect(newPasswordProperty.getValue()).andReturn(newPassword);
        
        // expect getting repeated new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.REPEAT_NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ repeatNewPasswordProperty});
        expect(repeatNewPasswordProperty.getValue()).andReturn(repeatNewPassword);
        
        // expect adding the invalid message because the password are not equal
        method(ChangePasswordFormValidator.class, "addInvalidMessage", ValidationContext.class, String.class, String.class);
        expectPrivate(validator, 
            "addInvalidMessage", 
            new Class[]{ValidationContext.class, String.class, String.class }, 
            ctxMock, 
            ChangePasswordFormValidator.PASSWORDS_NOT_EQUAL,
            null);
        
        replayAll();
        
        validator.validate(ctxMock);
        
        verifyAll();
    }
    
    /**
     * This test checks the {@link ChangePasswordFormValidator#validate(ValidationContext)} method in case
     * the new password is null.
     * <p>
     * The error must not be registered because the new password is null.
     */
    @Test
    public void newPasswordIsNullTest() {
        // variables
       String repeatNewPassword = getRandomChars(20);
        
        // mocks
        ValidationContext ctxMock = createMock(ValidationContext.class);
        Property newPasswordProperty = createMock(Property.class);
        Property repeatNewPasswordProperty = createMock(Property.class);
        
        // expect getting new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ newPasswordProperty });
        expect(newPasswordProperty.getValue()).andReturn(null);
        
        // expect getting repeated new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.REPEAT_NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ repeatNewPasswordProperty});
        expect(repeatNewPasswordProperty.getValue()).andReturn(repeatNewPassword);
        
        replayAll();
        
        validator.validate(ctxMock);
        
        verifyAll();
    }
    
    /**
     * This test checks the {@link ChangePasswordFormValidator#validate(ValidationContext)} method in case
     * the repeated new password is null.
     * <p>
     * The error must not be registered because the repeated new password is null.
     */
    @Test
    public void repeateNewPasswordIsNullTest() {
        // variables
       String newPassword = getRandomChars(20);
        
        // mocks
        ValidationContext ctxMock = createMock(ValidationContext.class);
        Property newPasswordProperty = createMock(Property.class);
        Property repeatNewPasswordProperty = createMock(Property.class);
        
        // expect getting new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ newPasswordProperty });
        expect(newPasswordProperty.getValue()).andReturn(newPassword);
        
        // expect getting repeated new password property from the validation context
        expect(ctxMock.getProperties(ChangePasswordFormVM.REPEAT_NEW_PASSWORD_PROPERTY)).
            andReturn(new Property[]{ repeatNewPasswordProperty});
        expect(repeatNewPasswordProperty.getValue()).andReturn(null);
        
        replayAll();
        
        validator.validate(ctxMock);
        
        verifyAll();
    }
}
