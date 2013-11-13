package org.panifex.platform.web.impl.main;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public abstract class AbstractVM {

	protected abstract Logger getLogger();
	
	@Command
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		getLogger().info("User {} is logging out", currentUser.getPrincipal());
		currentUser.logout();
		Executions.sendRedirect("/zk/login");
	}
	
	public boolean getIsUserLoggedIn() {
		Subject currentUser = SecurityUtils.getSubject();
		return currentUser.isAuthenticated();
	}
}
