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
package org.panifex.web.impl.view.main;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Executions;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractVMImpl.class, Executions.class, SecurityUtils.class})
public final class AbstractVMTest extends TestSupport {

    private AbstractVMImpl vm;

    @Before
    public void init() throws Exception {
        replayAll();

        vm = new AbstractVMImpl();
    }

    @Test
    public void testLogout() {
        Subject subjectMock = createMock(Subject.class);
        String principalMock = "principal";

        createMock(SecurityUtils.class);
        createMock(Executions.class);
        mockStatic(SecurityUtils.class);
        mockStatic(Executions.class);

        expect(SecurityUtils.getSubject()).andReturn(subjectMock);
        expect(subjectMock.getPrincipal()).andReturn(principalMock);
        subjectMock.logout();
        Executions.sendRedirect("/zk/login");

        replay(subjectMock);
        replayAll();

        vm.logout();

        verify(subjectMock);
        verifyAll();
    }
}
