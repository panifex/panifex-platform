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

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.zkoss.zk.ui.Executions;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractVMImpl.class, Executions.class, SecurityUtils.class})
public final class AbstractVMTest {

    private AbstractVMImpl vm;

    // mocks
    private Logger loggerMock = createMock(Logger.class);

    @Before
    public void init() throws Exception {
        reset(loggerMock);

        replayAll();

        vm = new AbstractVMImpl(loggerMock);
    }

    @Test
    public void testLogout() {
        Subject subjectMock = createMock(Subject.class);
        String principalMock = "principal";

        PowerMock.createMock(SecurityUtils.class);
        PowerMock.createMock(Executions.class);
        mockStatic(SecurityUtils.class);
        mockStatic(Executions.class);

        expect(SecurityUtils.getSubject()).andReturn(subjectMock);
        expect(subjectMock.getPrincipal()).andReturn(principalMock);
        loggerMock.info(anyObject(String.class), anyObject(String.class));
        subjectMock.logout();
        Executions.sendRedirect("/zk/login");

        replay(subjectMock, loggerMock);
        replayAll();

        vm.logout();

        verify(subjectMock, loggerMock);
        verifyAll();
    }
}
