package org.panifex.platform.web.impl.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.H1;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.PageCtrl;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Script;
import org.zkoss.zul.Textbox;

/**
 * Composes the login form.
 * 
 */
public class LoginFormRichlet extends GenericRichlet {

	private Logger log = LoggerFactory.getLogger(LoginFormRichlet.class);
	
	private Binder binder;
	
	@Override
	public void service(Page page) throws Exception {
		log.debug("Creating login page");
		PageCtrl pageCtrl = (PageCtrl) page;
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/bootstrap/css/bootstrap.min.css\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/index.css.dsp\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/login.css.dsp\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/grey.css.dsp\"/>");

		page.setTitle(Labels.getLabel("application.name"));

		final Div main = new Div();

		// initialize Binder
		binder = new DefaultBinder();
		binder.init(main, new LoginFormVM(), null);
		main.setAttribute("vm", binder.getViewModel());
		
		createTray(main);
		createContent(main);
		
		Script s = new Script();
		s.setSrc("../css/bootstrap/js/bootstrap.min.js");
		s.setParent(main);

		main.setPage(page);
		binder.loadComponent(main, true);
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
		logo.setHref(Labels.getLabel("application.url"));
		logo.setImage( "../img/panifex_top_logo.png");
		logo.setParent(container);

	}
	
	private void createContent(Component parent) {
		
		final Div content = new Div();
		content.setSclass("account-container");;
		
		final H1 h1 = new H1();
		h1.setParent(content);
		
		final Label h1Label = new Label(Labels.getLabel("login.form.header.title1"));
		h1Label.setParent(h1);
		
		final Div loginFields = new Div();
		loginFields.setSclass("login-fields");
		loginFields.setParent(content);
		
		final HtmlNativeComponent p = new HtmlNativeComponent("p");
		p.setPrologContent(Labels.getLabel("login.form.header.title2"));
		p.setParent(loginFields);
			
		final Div usernameField = new Div();
		usernameField.setSclass("field");
		usernameField.setParent(loginFields);
		
		// Creates username textbox
		final Textbox usernameTextbox = new Textbox();
		usernameTextbox.setSclass("username-field");
		usernameTextbox.setPlaceholder(Labels.getLabel("login.form.field.username.placeholder"));
		usernameTextbox.setPlaceholder(Labels.getLabel("login.form.field.username.tooltip"));
		usernameTextbox.setParent(usernameField);
		binder.addPropertyLoadBindings(usernameTextbox, "value", "vm.username", null, null, null, null, null);
		binder.addPropertySaveBindings(usernameTextbox, "value", "vm.username", null, null, null, null, null, null, null);
		
		final Div passwordField = new Div();
		passwordField.setSclass("field");
		passwordField.setParent(loginFields);
		
		// Creates password textbox
		final Textbox passwordTextbox = new Textbox();
		passwordTextbox.setSclass("password-field");
		passwordTextbox.setPlaceholder(Labels.getLabel("login.form.field.passwors.placeholder"));
		passwordTextbox.setTooltip(Labels.getLabel("login.form.field.passwors.tooltip"));
		passwordTextbox.setParent(passwordField);
		binder.addPropertyLoadBindings(passwordTextbox, "value", "vm.password", null, null, null, null, null);
		binder.addPropertySaveBindings(passwordTextbox, "value", "vm.password", null, null, null, null, null, null, null);
		
		final Div loginActions = new Div();
		loginActions.setSclass("login-actions");
		loginActions.setParent(content);
		
		// Creates remember me checkbox
		final Checkbox remembermeCheckbox = new Checkbox(Labels.getLabel("login.form.field.rememberme.label"));
		remembermeCheckbox.setSclass("field login-checkbox");
		remembermeCheckbox.setTooltip(Labels.getLabel("login.form.field.rememberme.tooltip"));
		remembermeCheckbox.setParent(loginActions);
		binder.addPropertyLoadBindings(remembermeCheckbox, "checked", "vm.isRememberMe", null, null, null, null, null);
		binder.addPropertySaveBindings(remembermeCheckbox, "checked", "vm.isRememberMe", null, null, null, null, null, null, null);
		
		// Creates sign in button
		final Button signInButton = new Button(Labels.getLabel("login.form.button.login.label"));
		signInButton.setClass("button btn btn-primary btn-large");
		signInButton.setParent(loginActions);
		signInButton.setType("submit");
		binder.addCommandBinding(signInButton, Events.ON_CLICK, "'signIn'", null);
		
		content.setParent(parent);
	}

}
 