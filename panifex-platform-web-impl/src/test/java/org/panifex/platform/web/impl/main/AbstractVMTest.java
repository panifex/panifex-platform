package org.panifex.platform.web.impl.main;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import javax.naming.InitialContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.platform.web.impl.sidebar.SidebarManager;
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
	private InitialContext initialContextMock = createMock(InitialContext.class);
	private SidebarManager sidebarManagerMock = createMock(SidebarManager.class);
	
	@Before
	public void init() throws Exception {
		reset(loggerMock, initialContextMock);
		PowerMock.createMock(InitialContext.class);
		PowerMock.expectNew(InitialContext.class).andReturn(initialContextMock);
		expect(initialContextMock.lookup("blueprint:comp/" + SidebarManager.ID)).andReturn(sidebarManagerMock);
		
		replay(initialContextMock);
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