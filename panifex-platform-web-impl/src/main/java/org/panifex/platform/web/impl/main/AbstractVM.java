package org.panifex.platform.web.impl.main;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.panifex.platform.module.api.sidebar.AbstractSidebarItem;
import org.panifex.platform.web.impl.sidebar.SidebarManager;
import org.slf4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public abstract class AbstractVM {

	protected abstract Logger getLogger();
	
	private SidebarManager sidebarManager;
	
	public AbstractVM() {
		try {
			InitialContext ctx = new InitialContext();
			sidebarManager = (SidebarManager) ctx.lookup("blueprint:comp/" + SidebarManager.ID);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		};
	}
	
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
	
	public List<AbstractSidebarItem> getSidebarItems() {
		return sidebarManager.getSidebarItems();
	}
}
