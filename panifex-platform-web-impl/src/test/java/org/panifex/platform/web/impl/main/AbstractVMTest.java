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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.zkoss.zk.ui.Executions;

@RunWith(PowerMockRunner.class) 
@PrepareForTest({Executions.class, SecurityUtils.class})
public final class AbstractVMTest {

	private AbstractVMImpl vm;
	
	private Logger logMock = createMock(Logger.class);
	
	@Before
	public void init() {
		reset(logMock);
		vm = new AbstractVMImpl(logMock);
	}
	
	@Test
	public void testLogout() {
		Subject subjectMock = createMock(Subject.class);
		String principalMock = "principal";
		
		mockStatic(SecurityUtils.class);
		mockStatic(Executions.class);
		
		expect(SecurityUtils.getSubject()).andReturn(subjectMock);
		expect(subjectMock.getPrincipal()).andReturn(principalMock);
		logMock.info(anyObject(String.class), anyObject(String.class));
		subjectMock.logout();
		Executions.sendRedirect("/zk/login");
		
		replay(subjectMock, logMock);
		replayAll();
		
		vm.logout();
		
		verify(subjectMock, logMock);
		verifyAll();
	}
}