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

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.service.api.security.AccountNotExpiredException;
import org.panifex.service.api.security.SecurityService;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.security.SecurityServiceManager;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * Unit tests for the {@link ChangePasswordFormVM} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    BindUtils.class,
    SecurityServiceManager.class,
    Sessions.class})
public final class ChangePasswordFormVMTest extends TestSupport {

    /**
     * The {@link ChangePasswordFormVM} instance for unit testing.
     */
    private ChangePasswordFormVM vm;
    
    // mocks
    private ChangePasswordFormController controllerMock =
            createMock(ChangePasswordFormController.class);
    
    /**
     * Initializes {@link ChangePasswordFormVM} before unit tests.
     */
    @Before
    public void setUp() {
        vm = new ChangePasswordFormVM(controllerMock);
        
        // mock static classes
        mockStatic(BindUtils.class);
        mockStatic(SecurityServiceManager.class);
        mockStatic(Sessions.class);
    }
    
    /**
     * Checks getting and setting the old password field.
     * <p>
     * The old password field must be null after VM has been initialized.
     */
    @Test
    public void setAndGetOldPasswordFieldTest() {
        // the old password field must be null after VM has been initialized
        assertNull(vm.getOldPassword());
        
        // set the old password
        String oldPassword = getRandomChars(20);
        vm.setOldPassword(oldPassword);
        
        // check the new password
        assertEquals(oldPassword, vm.getOldPassword());
    }
    
    /**
     * Checks getting and setting the new password field.
     * <p>
     * The new password field must be null after VM has been initialized.
     */
    @Test
    public void setAndGetNewPasswordFieldTest() {
        // the new password field must be null after VM has been initialized
        assertNull(vm.getNewPassword());
        
        // set the new password
        String newPassword = getRandomChars(20);
        vm.setNewPassword(newPassword);
        
        // check the new password
        assertEquals(newPassword, vm.getNewPassword());
    }
    
    /**
     * Checks getting and setting the repeat new password field.
     * <p>
     * The repeat new password field must be null after VM has been initialized.
     */
    @Test
    public void setAndGetRepeatNewPasswordFieldTest() {
        // the repeat new password field must be null after VM has been initialized
        assertNull(vm.getRepeatNewPassword());
        
        // set the repeat new password
        String repeatNewPassword = getRandomChars(20);
        vm.setRepeatNewPassword(repeatNewPassword);
        
        // check password 2
        assertEquals(repeatNewPassword, vm.getRepeatNewPassword());
    }
    
    /**
     * This tests checks the successfully initialization of {@link ChangePasswordFormVM} view-model.
     * <p>
     * It checks functionality of {@link ChangePasswordFormVM#init()} method when the parameters
     * are successfully passed.
     */
    @Test
    public void successfullyInitTest() {
        // variables
        String usernameParam = getRandomWord();
        Map<String, Object> attributesMock = new HashMap<>();
        attributesMock.put(ChangePasswordFormVM.USERNAME_PARAM, usernameParam);
        
        // mocks
        Session sessionMock = createMock(Session.class);
        
        // expect getting current session
        expect(Sessions.getCurrent()).andReturn(sessionMock);

        // expect removing the session attribute
        expect(sessionMock.removeAttribute(ChangePasswordFormVM.ID)).andReturn(attributesMock);
        
        replayAll();
        
        // initialize ChangePasswordFormVM
        vm.init();
        
        verifyAll();
    }
    
    /**
     * This tests checks the initialization of {@link ChangePasswordFormVM} in case the
     * parameters has not passed.
     * <p>
     * It checks functionality of {@link ChangePasswordFormVM#init()} method and expects that
     * the {@link ChangePasswordFormController#onWrongParametersPassed()} method is called.
     */
    @Test
    public void paramsNotPassedInitTest() {
        // mocks
        Session sessionMock = createMock(Session.class);
        
        // expect getting current session
        expect(Sessions.getCurrent()).andReturn(sessionMock);
        
        // expect removing the session attribute
        expect(sessionMock.removeAttribute(ChangePasswordFormVM.ID)).andReturn(null);
        
        // expect calling onWrongParametersPassed method
        controllerMock.onWrongParametersPassed();
        
        replayAll();
        
        // initialize ChangePasswordFormVM
        vm.init();
        
        verifyAll();
        
        // username must stay be null
        assertNull(vm.getUsername());
    }
    
    /**
     * This tests checks the initialization of {@link ChangePasswordFormVM} in case the
     * username parameter has not passed.
     * <p>
     * It checks functionality of {@link ChangePasswordFormVM#init()} method and expects that
     * the {@link ChangePasswordFormController#onWrongParametersPassed()} method is called.
     */
    @Test
    public void usernameParamNotPassedInitTest() {
        // variables
        Map<String, Object> attributesMock = new HashMap<>();
        
        // mocks
        Session sessionMock = createMock(Session.class);
        
        // expect getting current session
        expect(Sessions.getCurrent()).andReturn(sessionMock);

        // expect removing the session attribute
        expect(sessionMock.removeAttribute(ChangePasswordFormVM.ID)).andReturn(attributesMock);
        
        // expect calling controller's onWrongParametersPassed method
        controllerMock.onWrongParametersPassed();
        
        replayAll();
        
        // initialize ChangePasswordFormVM
        vm.init();
        
        verifyAll();
        
        // username must stay be null
        assertNull(vm.getUsername());
    }
    
    /**
     * This test checks the {@link ChangePasswordFormVM#changePassword()} method.
     * <p>
     * It is a successful test because the expired account's password has been
     * successfully changed.
     */
    @Test
    public void changePasswordCommandSuccessfulTest() throws Exception {
        // variables
        String username = getRandomChars(20);
        String oldPassword = getRandomChars(20);
        String newPassword = getRandomChars(20);
        String repeatNewPassword = newPassword;
        
        // bind variables to the view-model
        bindParamsToVM(username, oldPassword, newPassword, repeatNewPassword);
        
        // mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        // expect resolving the security service from manager
        expect(SecurityServiceManager.getService()).andReturn(securityServiceMock);
        
        // expect calling the SecurityService#updateAccountExpiredPassword method
        securityServiceMock.updateAccountExpiredPassword(username, oldPassword, newPassword);
        
        // expect calling controller's onSuccessfullyChangePassword method
        controllerMock.onSuccessfullyChangePassword(username);
        
        // expect reseting passwords
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.OLD_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.NEW_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.REPEAT_NEW_PASSWORD_ATTR);
        
        replayAll();
        
        vm.changePassword();
        
        verifyAll();
    }
    
    /**
     * This test checks the {@link ChangePasswordFormVM#changePassword()} method in case
     * the {@link org.panifex.service.api.security.SecurityService#updateAccountExpiredPassword(String, String, String)
     * SecurityService#updateAccountExpiredPassword(...)} throws the 
     * {@link org.apache.shiro.authc.UnknownAccountException UnknownAccountException} exception.
     */
    @Test
    public void changePasswordCommandUnknownAccountTest() throws Exception {
        // variables
        String username = getRandomChars(20);
        String oldPassword = getRandomChars(20);
        String newPassword = getRandomChars(20);
        String repeatNewPassword = newPassword;
        
        // bind variables to the view-model
        bindParamsToVM(username, oldPassword, newPassword, repeatNewPassword);
        
        // mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        // expect resolving the security service from manager
        expect(SecurityServiceManager.getService()).andReturn(securityServiceMock);
        
        // expect calling the SecurityService#updateAccountExpiredPassword method which throws UnknownAccountException
        securityServiceMock.updateAccountExpiredPassword(username, oldPassword, newPassword);
        expectLastCall().andThrow(new UnknownAccountException());
        
        // expect calling controller's onUnknownAccountException method
        controllerMock.onUnknownAccountException();
        
        // expect reseting passwords
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.OLD_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.NEW_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.REPEAT_NEW_PASSWORD_ATTR);
        
        replayAll();
        
        vm.changePassword();
        
        verifyAll();
        
        // assert that the password has been reseted
        assertNull(vm.getOldPassword());
        assertNull(vm.getNewPassword());
        assertNull(vm.getRepeatNewPassword());
    }
    
    /**
     * This test checks the {@link ChangePasswordFormVM#changePassword()} method in case
     * the {@link org.panifex.service.api.security.SecurityService#updateAccountExpiredPassword(String, String, String)
     * SecurityService#updateAccountExpiredPassword(...)} throws the 
     * {@link org.apache.shiro.authc.IncorrectCredentialsException IncorrectCredentialsException} exception.
     */
    @Test
    public void changePasswordCommandIncorrectCredentialsTest() throws Exception {
        // variables
        String username = getRandomChars(20);
        String oldPassword = getRandomChars(20);
        String newPassword = getRandomChars(20);
        String repeatNewPassword = newPassword;
        
        // bind variables to the view-model
        bindParamsToVM(username, oldPassword, newPassword, repeatNewPassword);
        
        // mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        // expect resolving the security service from manager
        expect(SecurityServiceManager.getService()).andReturn(securityServiceMock);
        
        // expect calling the SecurityService#updateAccountExpiredPassword method which throws UnknownAccountException
        securityServiceMock.updateAccountExpiredPassword(username, oldPassword, newPassword);
        expectLastCall().andThrow(new IncorrectCredentialsException());
        
        // expect calling controller's onIncorrectCredentialsException method
        controllerMock.onIncorrectCredentialsException();
        
        // expect reseting passwords
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.OLD_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.NEW_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.REPEAT_NEW_PASSWORD_ATTR);
        
        replayAll();
        
        vm.changePassword();
        
        verifyAll();
        
        // assert that the password has been reseted
        assertNull(vm.getOldPassword());
        assertNull(vm.getNewPassword());
        assertNull(vm.getRepeatNewPassword());
    }
    
    /**
     * This test checks the {@link ChangePasswordFormVM#changePassword()} method in case
     * the {@link org.panifex.service.api.security.SecurityService#updateAccountExpiredPassword(String, String, String)
     * SecurityService#updateAccountExpiredPassword(...)} throws the 
     * {@link org.panifex.service.api.security.AccountNotExpiredException AccountNotExpiredException} exception.
     */
    @Test
    public void changePasswordCommandAccountNotExpiredTest() throws Exception {
        // variables
        String username = getRandomChars(20);
        String oldPassword = getRandomChars(20);
        String newPassword = getRandomChars(20);
        String repeatNewPassword = newPassword;
        
        // bind variables to the view-model
        bindParamsToVM(username, oldPassword, newPassword, repeatNewPassword);
        
        // mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);
        
        // expect resolving the security service from manager
        expect(SecurityServiceManager.getService()).andReturn(securityServiceMock);
        
        // expect calling the SecurityService#updateAccountExpiredPassword method which throws UnknownAccountException
        securityServiceMock.updateAccountExpiredPassword(username, oldPassword, newPassword);
        expectLastCall().andThrow(new AccountNotExpiredException());
        
        // expect calling controller's onAccountNotExpiredException method
        controllerMock.onAccountNotExpiredException();
        
        // expect reseting passwords
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.OLD_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.NEW_PASSWORD_ATTR);
        BindUtils.postNotifyChange(null, null, vm, ChangePasswordFormVM.REPEAT_NEW_PASSWORD_ATTR);
        
        replayAll();
        
        vm.changePassword();
        
        verifyAll();
        
        // assert that the password has been reseted
        assertNull(vm.getOldPassword());
        assertNull(vm.getNewPassword());
        assertNull(vm.getRepeatNewPassword());
    }
    
    /**
     * Binds parameters to the {@link ChangePasswordFormVM}.
     * 
     * @param username the account's username
     * @param oldPassword the account's current old password
     * @param newPassword the account's new password
     * @param repeatNewPassword the account's repeated new password
     */
    private void bindParamsToVM(
            String username, 
            String oldPassword, 
            String newPassword, 
            String repeatNewPassword) {
        
        // create the attributes map
        Map<String, Object> attributesMock = new HashMap<>();
        attributesMock.put(ChangePasswordFormVM.USERNAME_PARAM, username);
        
        // mocks
        Session sessionMock = createMock(Session.class);
        
        // expect getting current session
        expect(Sessions.getCurrent()).andReturn(sessionMock);

        // expect removing the session attribute
        expect(sessionMock.removeAttribute(ChangePasswordFormVM.ID)).andReturn(attributesMock);
        
        replayAll();
        
        // set username during the initialization
        vm.init();
        
        verifyAll();
        resetAll();
        
        // set passwords to the view-model
        vm.setOldPassword(oldPassword);
        vm.setNewPassword(newPassword);
        vm.setRepeatNewPassword(repeatNewPassword);
    }
}
