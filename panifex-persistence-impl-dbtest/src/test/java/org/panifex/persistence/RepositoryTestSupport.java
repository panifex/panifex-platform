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
package org.panifex.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.junit.Before;
import org.panifex.test.support.TestSupport;

/**
 * The template class for unit testing of repository implementations. 
 * <p>
 * It prepares an in-memory database for performing unit tests.
 */
public abstract class RepositoryTestSupport extends TestSupport {

    private static final String MASTER_CHANGELOG = "../panifex-persistence-impl/src/main/resources/db-changelog/db.changelog-0.1.0.xml";
    private static final String TEST_DATA_CHANGELOG = "src/test/resources/db-changelog/db.changelog-test.xml";
    
    protected EntityManager entityManager;
    
    @Before
    public final void setUp() throws SQLException, LiquibaseException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("panifex");
        entityManager = entityManagerFactory.createEntityManager();
        
        OpenJPAEntityManagerFactorySPI kemf = (OpenJPAEntityManagerFactorySPI) OpenJPAPersistence.cast(entityManagerFactory);
        OpenJPAConfiguration conf = kemf.getConfiguration();
        DataSource dataSource = (DataSource) conf.getConnectionFactory();
        Connection conn = dataSource.getConnection();
                
        createTables(conn);
        insertTestData(conn);
    }

    private void createTables(Connection conn) throws LiquibaseException {
        executeChangelogs(conn, MASTER_CHANGELOG);
    }
    
    private void insertTestData(Connection conn) throws LiquibaseException {
        executeChangelogs(conn, TEST_DATA_CHANGELOG);
    }
    
    private void executeChangelogs(Connection conn, String changelog) throws LiquibaseException {
        Liquibase liquibase = new Liquibase(
            changelog, 
            new FileSystemResourceAccessor(), 
            new JdbcConnection(conn));
        
        liquibase.update(null);
    }
}
