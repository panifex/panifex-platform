/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.platform.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.osgi.framework.BundleContext;
import org.panifex.platform.persistence.resource.BundleResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = "org.panifex.platform.persistence.DataSourceListener")
@ReferenceListener
public class DataSourceListener {

    private Logger log = LoggerFactory.getLogger(DataSourceListener.class);

    @Inject(ref = "blueprintBundleContext")
    private BundleContext bundleContext;

    @Inject
    @Reference(serviceInterface = DataSource.class, filter = "(osgi.jndi.service.name=jdbc/PanifexDataSource)", referenceListeners = @ReferenceListener(ref = "org.panifex.platform.persistence.DataSourceListener"))
    private DataSource dataSource;

    private String changeLogFile = "/db-changelog/db.changelog-master.xml";;
    private String defaultSchema = "";
    private String contexts = "";

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Bind
    public void bind(DataSource dataSource) {
        try {
            log.debug("Fetch data source");
            Connection connection = dataSource.getConnection();

            BundleResourceAccessor bundleResAccessor = new BundleResourceAccessor(bundleContext);

            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                            new JdbcConnection(connection));
            // database.setDefaultSchemaName(getDefaultSchema());

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
    }

    @Unbind
    public void unbind(DataSource dataSource) {

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