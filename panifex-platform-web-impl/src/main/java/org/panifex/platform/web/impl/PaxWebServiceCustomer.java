package org.panifex.platform.web.impl;

import java.util.Hashtable;

import javax.servlet.ServletException;

import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.ops4j.pax.web.service.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.dsp.InterpreterServlet;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

/**
 * Listens to Pax Web service and register/unregister ZK servlets.
 * 
 * @author Mario Krizmanic (mario.krizmanic@gmail.com)
 *
 */
public final class PaxWebServiceCustomer implements ServiceTrackerCustomizer {

	private Logger log = LoggerFactory.getLogger(PaxWebServiceCustomer.class);
	
	private BundleContext context;
	
	/**
	 * Construct the new Pax Web service customer.
	 * 
	 * @param context The OSGi bundle context
	 */
	public PaxWebServiceCustomer(BundleContext context) {
		this.context = context;
	}
	
	@Override
	public Object addingService(ServiceReference reference) {
		
		WebContainer http = (WebContainer) context.getService(reference);
		
		// configure zkLoader servlet
		log.debug("Configure ZkLoader servlet");
		DHtmlLayoutServlet zkLoader = new DHtmlLayoutServlet();
		Hashtable<String, String> loaderParams = new Hashtable<>();
		loaderParams.put("servlet-name", "zkLoader");
		loaderParams.put("update-uri", "/zkau"); // URI mapped to auEngine
		String loaderMapping[] = {"*.zul", "*.html"}; // mapping of UI files
		
		// configure auEngine servlet
		DHtmlUpdateServlet auEngine = new DHtmlUpdateServlet();
		Hashtable<String, String> auEngineParams = new Hashtable<>();
		auEngineParams.put("servlet-name", "auEngine");
		String engineMapping[] = {"/zkau/*"}; // same URI as the parameter "update-uri" of zkLoader
		
		// configure dsp servlet
		InterpreterServlet dspServlet = new InterpreterServlet();
		Hashtable<String, String> dspParams = new Hashtable<>();
		dspParams.put("class-resource", "true");
		String dspMapping[] = {"*.dsp"}; // mapping of DSP files
		
		// configure security filter
		EnvironmentLoaderListener secListener = new EnvironmentLoaderListener();
		String secServletNames[] = {"zkLoader"};
		String secFilterMapping[] = {"/*"};
		ShiroFilter secFilter = new ShiroFilter();
		
		// get the http context (zk servlets should be registered with the same http context)
		HttpContext ctx = http.createDefaultHttpContext();
		// register zk session listener
		http.registerEventListener(new HttpSessionListener(), ctx);
		
		try {
			// register zk servlets
			http.registerServlet(zkLoader, loaderMapping, loaderParams, ctx);
			http.registerServlet(auEngine, engineMapping, auEngineParams, ctx);
			http.registerServlet(dspServlet, dspMapping, dspParams, ctx);
			log.debug("Zk servlets have been registered");
			
			// register security filter
			http.registerEventListener(secListener, ctx);
			http.registerFilter(secFilter, secFilterMapping, secServletNames, null, ctx);
			log.debug("Security filter has been registered");
			
			// register resources
			http.registerResources("/", "/", ctx);
				
		} catch (ServletException e) {
			log.error("Unable to register zk servlets", e);
		} catch (NamespaceException e) {
			log.error("Unable to register resources", e);
		}
		
		return http;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		
		
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		context.ungetService(reference);
		
	}

}
