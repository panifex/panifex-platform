package org.panifex.platform.web.impl;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.ops4j.pax.web.service.WebContainer;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.panifex.platform.web.impl.security.SecurityFilter;
import org.panifex.platform.web.impl.security.SecurityRealmListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.dsp.InterpreterServlet;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

@Bean(id = "org.panifex.platform.web.impl.PaxWebServiceListener")
@ReferenceListener
public class PaxWebServiceListener {

	private Logger log = LoggerFactory.getLogger(SecurityRealmListener.class);

	@Inject
	@Reference(availability = "optional", serviceInterface = WebContainer.class, referenceListeners = @ReferenceListener(ref = "org.panifex.platform.web.impl.PaxWebServiceListener"))
	private WebContainer container;

	@Inject(ref = "org.panifex.platform.web.impl.security.SecurityFilter")
	private SecurityFilter securityFilter;
	
	public void setSecurityFilter(SecurityFilter securityFilter) {
		this.securityFilter = securityFilter;
	}
	
	@Bind
	public void bind(WebContainer container) {
		// configure zkLoader servlet
		log.debug("Configure ZkLoader servlet");
		DHtmlLayoutServlet zkLoader = new DHtmlLayoutServlet();
		Hashtable<String, String> loaderParams = new Hashtable<>();
		loaderParams.put("servlet-name", "zkLoader");
		loaderParams.put("update-uri", "/zkau"); // URI mapped to auEngine
		String loaderMapping[] = { "*.zul", "*.html" }; // mapping of UI files

		// configure auEngine servlet
		log.debug("Configure auEngine servlet");
		DHtmlUpdateServlet auEngine = new DHtmlUpdateServlet();
		Hashtable<String, String> auEngineParams = new Hashtable<>();
		auEngineParams.put("servlet-name", "auEngine");
		String engineMapping[] = { "/zkau/*" }; // same URI as the parameter
												// "update-uri" of zkLoader
		// configure dsp servlet
		log.debug("Configure dsp servlet");
		InterpreterServlet dspServlet = new InterpreterServlet();
		Hashtable<String, String> dspParams = new Hashtable<>();
		dspParams.put("class-resource", "true");
		String dspMapping[] = { "*.dsp" }; // mapping of DSP files

		// configure security filter
		log.debug("Configure security filter");
		EnvironmentLoaderListener secListener = new EnvironmentLoaderListener();
		String secServletNames[] = { "zkLoader" };
		String secFilterMapping[] = { "/*" };

		// get the http context (zk servlets should be registered with the same
		// http context)
		HttpContext ctx = container.createDefaultHttpContext();
		// register zk session listener
		container.registerEventListener(new HttpSessionListener(), ctx);

		try {
			// register zk servlets
			container.registerServlet(zkLoader, loaderMapping, loaderParams,
					ctx);
			container.registerServlet(auEngine, engineMapping, auEngineParams,
					ctx);
			container.registerServlet(dspServlet, dspMapping, dspParams, ctx);
			log.debug("Zk servlets have been registered");

			// register security filter
			container.registerEventListener(secListener, ctx);
			container.registerFilter(securityFilter, secFilterMapping,
					secServletNames, null, ctx);
			log.debug("Security filter has been registered");

			// register resources
			container.registerResources("/", "/", ctx);

		} catch (ServletException e) {
			log.error("Unable to register zk servlets", e);
		} catch (NamespaceException e) {
			log.error("Unable to register resources", e);
		}
	}

	@Unbind
	public void unbind(WebContainer container) {

	}
}
