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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.view.login.LoginFormVM;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Executions;

/**
 * Unit tests for the {@link LoginFormVM} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginFormVM.class, Executions.class, SecurityUtils.class})
public class LoginFormVMTest extends TestSupport {

    /**
     * The {@link LoginFormVM} instance for unit testing.
     */
    private LoginFormVM vm;

    // mocks
    private LoginFormController controllerMock = createMock(LoginFormController.class);
    
    /**
     * Initializes {@link LoginFormVM} before performing unit tests.
     */
    @Before
    public void init() throws Exception {
        // contruct the view-model
        vm = new LoginFormVM(controllerMock);
       
        // mock static classes
        mockStatic(SecurityUtils.class);
        mockStatic(Executions.class);
    }

    /**
     * This test tests the successful log in process.
     * <p>
     * The {@link LoginFormController#onSuccessfulLoginIn()} method must be called
     * after the user has successfully been logged in.
     */
    @Test
    public void successfulLoginTest() throws Exception {
        // variables
        String username = getRandomWord();
        String password = getRandomWord();
        boolean isRememberMe = chance(50);
        
        // bind variables to the view-model
        vm.setUsername(username);
        vm.setPassword(password);
        vm.setIsRememberMe(isRememberMe);
        
        // mocks
        UsernamePasswordToken tokenMock = 
                createMockAndExpectNew(UsernamePasswordToken.class, username, password);
        Subject subjectMock = createMock(Subject.class);

        // expect setting the is remember me flag
        tokenMock.setRememberMe(isRememberMe);
        
        // expect getting the current subject
        expect(SecurityUtils.getSubject()).andReturn(subjectMock);
        
        // expect throwing the ExpiredCredentialsException during the login
        subjectMock.login(tokenMock);

        // expect calling the controller's onSuccessfulLoginIn method
        controllerMock.onSuccessfulLoginIn();
        
        // perform test
        replayAll();
        vm.signIn();
        verifyAll();
    }
    
    /**
     * This test simulates the case when the user tries to log in with the expired account.
     * <p>
     * The view-model must call the {@link LoginFormController#onExpiredCredentialsException(String)} method,
     * because the account is expired.
     */
    @Test
    public void onExpiredCredentialsTest() throws Exception {
        // variables
        String username = getRandomWord();
        String password = getRandomWord();
        boolean isRememberMe = chance(50);
        
        // bind variables to the view-model
        vm.setUsername(username);
        vm.setPassword(password);
        vm.setIsRememberMe(isRememberMe);
        
        // mocks
        UsernamePasswordToken tokenMock = 
                createMockAndExpectNew(UsernamePasswordToken.class, username, password);
        Subject subjectMock = createMock(Subject.class);
        
        // expect setting the is remember me flag
        tokenMock.setRememberMe(isRememberMe);
        
        // expect getting the current subject
        expect(SecurityUtils.getSubject()).andReturn(subjectMock);
        
        // expect throwing the ExpiredCredentialsException during the login
        subjectMock.login(tokenMock);
        expectLastCall().andThrow(new ExpiredCredentialsException());
        
        // expect calling the controller's onExpiredCredentialsException method
        controllerMock.onExpiredCredentialsException(username);
        
        // perform test
        replayAll();
        vm.signIn();        
        verifyAll();
    }
}
