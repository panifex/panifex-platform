package org.panifex.platform.web.impl.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class LoginFormVM {

	private Logger log = LoggerFactory.getLogger(LoginFormVM.class);
	
	private String username = "";
	private String password = "";
	private boolean isRememberMe = true;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getIsRememberMe() {
		return isRememberMe;
	}
	public void setIsRememberMe(boolean isRemember) {
		this.isRememberMe = isRemember;
	}
	
	@Command
	public void signIn() {
		log.debug("User is signing in");
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(isRememberMe);
		Subject currentUser = SecurityUtils.getSubject();
		
		try {
			currentUser.login(token);
			Executions.sendRedirect("/zk/main");
		} catch (UnknownAccountException e) {
			Messagebox.show("Your user name or password is incorrect.", null, 0, Messagebox.ERROR);
		} catch (IncorrectCredentialsException e) {
			Messagebox.show("Your user name or password is incorrect.", null, 0, Messagebox.ERROR);
		} catch (ExcessiveAttemptsException e) {
			Messagebox.show("", null, 0, Messagebox.ERROR);
		} catch (ExpiredCredentialsException e) {
		} catch (LockedAccountException e) {
		} catch (DisabledAccountException e) {
		
		} catch (ConcurrentAccessException e) { 
		} catch (AccountException e) {		
		
		} catch (AuthenticationException e) {
			Messagebox.show("An error during the Authentication process");
		}
	}
}
