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

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.web.impl.view.login.LoginFormVM;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginFormVM.class, Executions.class, LoggerFactory.class, SecurityUtils.class})
public class LoginFormVMTest {

    private LoginFormVM vm;

    private Logger loggerMock = createMock(Logger.class);;

    @Before
    public void init() throws Exception {
        mockStatic(LoggerFactory.class);
        expect(LoggerFactory.getLogger(LoginFormVM.class)).andReturn(loggerMock);

        replayAll();
        vm = new LoginFormVM();
        verifyAll();
        resetAll();
    }

    @Test
    public void testLogin() throws Exception {
        UsernamePasswordToken token = createMock(UsernamePasswordToken.class);
        Subject subjectMock = createMock(Subject.class);

        mockStatic(SecurityUtils.class);
        mockStatic(Executions.class);

        loggerMock.debug(anyObject(String.class));
        PowerMock.createMock(UsernamePasswordToken.class);
        PowerMock.expectNew(UsernamePasswordToken.class,
                new Class<?>[] {String.class, String.class}, eq(""), eq("")).andReturn(token);
        token.setRememberMe(true);
        expect(SecurityUtils.getSubject()).andReturn(subjectMock);
        subjectMock.login(token);
        Executions.sendRedirect("/zk/main");

        replay(token, subjectMock);
        replayAll();

        vm.signIn();

        verify(token, subjectMock);
        verifyAll();
    }
}