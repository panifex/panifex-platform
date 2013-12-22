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
import java.util.List;

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
import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;

/**
 * Test cases for {@link AccountRepositoryImpl} class. 
 *
 */
public final class AccountRepositoryImplTest extends TestSupport {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMINISTRATOR_ROLE = "Administrator";
    private static final String USER_PERMISSION = "user";
    
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
     * 
     * <p>The test also checks functionality of {@link AccountRepositoryImpl#getAccountByUsername(String)} method.
     * 
     * <p>Admin user must be found.
     */
    @Test
    public void getAccountByUsernameTest() {
        AccountEntity account = accountRepository.getAccountByUsername(ADMIN_USERNAME);
        
        // must be admin
        assertNotNull(account);
        assertEquals(ADMIN_USERNAME, account.getUsername());
    }
    
    /**
     * This test checks if the admin user has administrator role. The administrator role must be
     * inserted by liquibase scripts.
     * 
     * <p>The test also checks functionality of {@link AccountRepositoryImpl#getRolesForAccount(org.panifex.service.api.security.Account)} 
     * method.
     */
    @Test
    public void getRolesForAccountTest() {
        // get admin account
        AccountEntity adminAccount = accountRepository.getAccountByUsername(ADMIN_USERNAME);
        
        // must be admin
        assertEquals(ADMIN_USERNAME, adminAccount.getUsername());
        
        // get admin's roles
        List<RoleEntity> roles = accountRepository.getRolesForAccount(adminAccount);
        
        // check if admin user has administration role
        assertNotNull(roles);
        assertEquals(ADMINISTRATOR_ROLE, roles.get(0).getName());
    }
    
    /**
     * This test checks if the admin user has user permission. The user permission must be inserted
     * by liquibase scripts.
     * 
     * <p>The test also checks functionality of {@link AccountRepositoryImpl#getPermissionsForAccount(org.panifex.service.api.security.Account)}
     * method.
     */
    @Test
    public void getPermissionsForAccountTest() {
        // get admin account
        AccountEntity adminAccount = accountRepository.getAccountByUsername(ADMIN_USERNAME);
        
        // must be admin
        assertEquals(ADMIN_USERNAME, adminAccount.getUsername());
        
        // get admin's permissions
        List<PermissionEntity> permissions = accountRepository.getPermissionsForAccount(adminAccount);
        
        // check if admin user has user permission
        assertNotNull(permissions);
        assertEquals(USER_PERMISSION, permissions.get(0).getWildcardExpression());
    }
}
