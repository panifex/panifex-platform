package org.panifex.platform.web.impl.login;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.resetAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import javax.naming.InitialContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.platform.web.impl.sidebar.SidebarManager;
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
    private InitialContext initialContextMock = createMock(InitialContext.class);
    private SidebarManager sidebarManagerMock = createMock(SidebarManager.class);

    @Before
    public void init() throws Exception {

        reset(initialContextMock);

        mockStatic(LoggerFactory.class);
        expect(LoggerFactory.getLogger(LoginFormVM.class)).andReturn(loggerMock);
        PowerMock.createMock(InitialContext.class);
        PowerMock.expectNew(InitialContext.class).andReturn(initialContextMock);
        expect(initialContextMock.lookup("blueprint:comp/" + SidebarManager.ID)).andReturn(
                sidebarManagerMock);

        replay(initialContextMock);
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
