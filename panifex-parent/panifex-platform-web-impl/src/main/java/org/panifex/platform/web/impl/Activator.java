package org.panifex.platform.web.impl;

import org.ops4j.pax.web.service.WebContainer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator implements BundleActivator {

	private Logger log = LoggerFactory.getLogger(Activator.class);;

	private ServiceTracker paxWebServiceTracker;
	
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		log.info("STARTING org.panifex.platform.web.impl");

		// register listener to the Pax Web service
		paxWebServiceTracker = new ServiceTracker(bc,
				WebContainer.class.getName(), new PaxWebServiceCustomer(bc));
		paxWebServiceTracker.open();
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		log.info("STOPPING org.panifex.platform.web.impl");

		paxWebServiceTracker.close();

	}
}
