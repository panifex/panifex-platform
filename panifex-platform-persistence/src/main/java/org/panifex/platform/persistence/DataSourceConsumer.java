package org.panifex.platform.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.panifex.platform.persistence.resource.BundleResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceConsumer implements ServiceTrackerCustomizer {

	private Logger log = LoggerFactory.getLogger(DataSourceConsumer.class);
	
	private BundleContext context;
	
	private String changeLogFile;
	private String defaultSchema;
	private String contexts;
	
	public DataSourceConsumer(BundleContext context) {
		log.debug("Creating Data Source consumer");
		
		this.context = context;
		
		changeLogFile = "/db-changelog/db.changelog-master.xml";
		defaultSchema = "";
		contexts = "";
	}
	
	@Override
	public Object addingService(ServiceReference reference) {

		log.info("Subscribing to data source");
		
		DataSource dataSource = (DataSource) context.getService(reference);

		try {
			log.debug("Fetch data source");
			Connection connection = dataSource.getConnection();

			//Thread currentThread = Thread.currentThread();
			BundleResourceAccessor bundleResAccessor = new BundleResourceAccessor(context);
			//ClassLoader contextClassLoader = currentThread.getContextClassLoader();
			//ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);
			
			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(connection));
            //database.setDefaultSchemaName(getDefaultSchema());
			
			Liquibase liquibase = new Liquibase(getChangeLogFile(), bundleResAccessor, database);
			
			log.debug("Start updating data source schemas");
			liquibase.update(contexts);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (DatabaseException e) {
			throw new RuntimeException(e);
		} catch (LiquibaseException e) {
			throw new RuntimeException(e);
		}
		return reference;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		context.ungetService(reference);
	}
	
	protected String getChangeLogFile() {
		return changeLogFile;
	}
	
	protected String getDefaultSchema() {
		return defaultSchema;
	}

	protected String getContexts() {
		return contexts;
	}
}
