package org.panifex.platform.web.impl.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.sys.PageCtrl;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Script;
import org.zkoss.zul.Textbox;

public class LoginRichlet extends GenericRichlet {

	private Logger log = LoggerFactory.getLogger(LoginRichlet.class);
	
	@Override
	public void service(Page page) throws Exception {
		log.debug("Creating login page");
		PageCtrl pageCtrl = (PageCtrl) page;
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/bootstrap/css/bootstrap.min.css\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/index.css.dsp\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/login.css.dsp\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/grey.css.dsp\"/>");
		
		page.setTitle("Hello Mario!!!");

		final Div main = new Div();
				
		createTray(main);
		createContent(main);
		
		Script s = new Script();
		s.setSrc("../css/bootstrap/js/bootstrap.min.js");
		s.setParent(main);

		main.setPage(page);
	}
	
	private void createTray(Component parent) {
		
		final Div header = new Div();
		header.setSclass("header");
		header.setParent(parent);
		
		final Div fill = new Div();
		fill.setSclass("fill");
		fill.setParent(header);
		
		final Div container = new Div();
		container.setSclass("container");
		container.setParent(fill);
		
		
		final A logo = new A();
		logo.setHref("http://www.panifex.org/");
		logo.setImage( "../img/panifex_top_logo.png");
		logo.setParent(container);

	}
	
	private void createContent(Component parent) {
		
		final Div content = new Div();
		content.setSclass("account-container");
			
		final HtmlNativeComponent h1 = new HtmlNativeComponent("h1");
		h1.setPrologContent("Sign In");
		h1.setParent(content);
		
		final Div loginFields = new Div();
		loginFields.setSclass("login-fields");
		loginFields.setParent(content);
		
		final HtmlNativeComponent p = new HtmlNativeComponent("p");
		p.setPrologContent("Sign in using your registered account");
		p.setParent(loginFields);
			
		final Div usernameField = new Div();
		usernameField.setSclass("field");
		usernameField.setParent(loginFields);
		
		final Textbox usernameTextbox = new Textbox();
		usernameTextbox.setSclass("username-field");
		usernameTextbox.setPlaceholder("Username");
		usernameTextbox.setParent(usernameField);
		
		final Div passwordField = new Div();
		passwordField.setSclass("field");
		passwordField.setParent(loginFields);
		
		final Textbox passwordTextbox = new Textbox();
		passwordTextbox.setSclass("password-field");
		passwordTextbox.setPlaceholder("Password");
		passwordTextbox.setParent(passwordField);
		
		final Div loginActions = new Div();
		loginActions.setSclass("login-actions");
		loginActions.setParent(content);
		
		final Checkbox remembermeCheckbox = new Checkbox("Keep me signed in");
		remembermeCheckbox.setSclass("field login-checkbox");
		remembermeCheckbox.setParent(loginActions);
		
		final Button signInButton = new Button("Sign In");
		signInButton.setSclass("button btn btn-primary btn-large");
		signInButton.setParent(loginActions);
		
		content.setParent(parent);
	}

}
 