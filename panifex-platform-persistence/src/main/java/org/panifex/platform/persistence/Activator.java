package org.panifex.platform.persistence;

import javax.sql.DataSource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator implements BundleActivator {

	private Logger log = LoggerFactory.getLogger(Activator.class);
	
	private ServiceTracker dataSourceTracker; 
	
	/**
	 * Called whenever the OSGi framework starts panifex-platform-persistence bundle
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		log.info("STARTING panifex-platform-persistence bundle");
		
		String filterExpr = "(objectclass=" + DataSource.class.getName() + ")";
		Filter filter = context.createFilter(filterExpr);
		
		DataSourceConsumer consumer = new DataSourceConsumer(context);
		
		dataSourceTracker = new ServiceTracker(context,
				filter, consumer);
		dataSourceTracker.open();
		
	}

	/**
	 * Called whenever the OSGi framework stops panifex-platform-persistence bundle
	 */
	@Override
	public void stop(BundleContext arg0) throws Exception {
		log.info("STOPPING panifex-platform-persistence");
		
		dataSourceTracker.close();
	}

}
