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

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginFormVM.class, Executions.class, SecurityUtils.class})
public class LoginFormVMTest extends TestSupport {

    private LoginFormVM vm;

    @Before
    public void init() throws Exception {
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

        createMock(UsernamePasswordToken.class);
        expectNew(UsernamePasswordToken.class,
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
