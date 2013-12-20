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
package org.panifex.persistence.security;

import java.sql.Connection;
import java.sql.SQLException;

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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class AccountRepositoryImplTest {

    private static final String USERNAME = "admin";
    
    private EntityManagerFactory entityManagerFactory;
    private AccountRepositoryImpl accountRepository;
    
    @Before
    public void setUp() throws SQLException, LiquibaseException {
        entityManagerFactory = Persistence.createEntityManagerFactory("panifex");
        
        OpenJPAEntityManagerFactorySPI kemf = (OpenJPAEntityManagerFactorySPI) OpenJPAPersistence.cast(entityManagerFactory);
        OpenJPAConfiguration conf = kemf.getConfiguration();
        DataSource dataSource = (DataSource) conf.getConnectionFactory();
        Connection conn = dataSource.getConnection();
        Liquibase liquibase = new Liquibase("src/main/resources/db-changelog/db.changelog-master.xml", new FileSystemResourceAccessor(), new JdbcConnection(conn));
        liquibase.update(null);
        
        accountRepository = new AccountRepositoryImpl();
        accountRepository.bind(entityManagerFactory);
    }
    
    @After
    public void after() {
        accountRepository.unbind(entityManagerFactory);
    }
    
    /**
     * This test checks if the admin user is inserted to initial database created by liquibase scripts.
     * The test also checks functionality of AccountRepository.getAccountByUsername method
     * 
     * Admin user must be found.
     */
    @Test
    public void getAccountByUsernameTest() {
        AccountEntity account = accountRepository.getAccountByUsername(USERNAME);
        
        // assert must be admin
        Assert.assertNotNull(account);
        Assert.assertEquals(USERNAME, account.getUsername());
    }
}
