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
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Before;
import org.junit.Test;
import org.panifex.service.api.security.Permission;
import org.panifex.service.api.security.Role;
import org.panifex.test.support.TestSupport;

/**
 * Test cases for {@link AccountRepositoryImpl} class. 
 *
 */
public final class AccountRepositoryImplTest extends TestSupport {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMINISTRATOR_ROLE = "Administrator";
    private static final String USER_PERMISSION = "user";
    
    private EntityManager entityManager;
    private AccountRepositoryImpl accountRepository;
    
    @Before
    public void setUp() throws SQLException, LiquibaseException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("panifex");
        entityManager = entityManagerFactory.createEntityManager();
        
        OpenJPAEntityManagerFactorySPI kemf = (OpenJPAEntityManagerFactorySPI) OpenJPAPersistence.cast(entityManagerFactory);
        OpenJPAConfiguration conf = kemf.getConfiguration();
        DataSource dataSource = (DataSource) conf.getConnectionFactory();
        Connection conn = dataSource.getConnection();
        Liquibase liquibase = new Liquibase("../panifex-persistence-impl/src/main/resources/db-changelog/db.changelog-master.xml", 
            new FileSystemResourceAccessor(), new JdbcConnection(conn));
        liquibase.update(null);
        
        accountRepository = new AccountRepositoryImpl();
    }
    
    /**
     * This test tries to update an existed account. It checks functionality of
     * {@link AccountRepositoryImpl#updateAccount(AccountEntity)} method.
     * <p>
     * For starter the test gets an existed Account.
     */
    @Test
    public void updateAccountTest() {
        // variables
        DataFactory df = new DataFactory();
        String newPassword = df.getRandomChars(20);
                
        // get the persisted account
        AccountEntity account = accountRepository.getAccountByUsername(entityManager, ADMIN_USERNAME);
        
        // must be admin
        assertNotNull(account);
        assertEquals(ADMIN_USERNAME, account.getUsername());
        assertNotEquals(newPassword, account.getPassword());
        
        // change their password
        account.setPassword(newPassword);
        
        // save the changed account
        accountRepository.updateAccount(entityManager, account);
        
        // check if the account has been changed
        AccountEntity changedAccount = accountRepository.getAccountByUsername(entityManager, ADMIN_USERNAME);
        
        assertEquals(account.getId(), changedAccount.getId());
        assertEquals(newPassword, changedAccount.getPassword());
    }
    
    /**
     * This test checks if the admin user exists in an initial database created by liquibase scripts.
     * 
     * <p>The test also checks functionality of {@link AccountRepositoryImpl#getAccountByUsername(String)} 
     * method.
     * 
     * <p>Admin user must be found.
     */
    @Test
    public void getAccountByUsernameTest() {
        AccountEntity account = accountRepository.getAccountByUsername(entityManager, ADMIN_USERNAME);
        
        // must be admin
        assertNotNull(account);
        assertNotNull(account.getId());
        assertEquals(ADMIN_USERNAME, account.getUsername());
        assertNotNull(account.getPassword());
        assertNotNull(account.getPasswordSalt());
        assertEquals(true, account.getIsCredentialsExpired());
        
        // check password
        byte[] salt = Base64.decode(account.getPasswordSalt());
        String hashedPasswordBase64 = new SimpleHash(PersistenceRealm.HASH_ALGORITHM, ADMIN_PASSWORD, salt, PersistenceRealm.HASH_ITERATIONS).toBase64();
        assertEquals(hashedPasswordBase64, account.getPassword());
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
        AccountEntity adminAccount = accountRepository.getAccountByUsername(entityManager, ADMIN_USERNAME);
        
        // must be admin
        assertEquals(ADMIN_USERNAME, adminAccount.getUsername());
        
        // get admin's roles
        List<? extends Role> roles = accountRepository.getRolesForAccount(entityManager, adminAccount);
        
        // check if admin user has administration role
        assertNotNull(roles);
        assertEquals(ADMINISTRATOR_ROLE, roles.get(0).getName());
    }
    
    /**
     * This test checks if the admin user has the user permission. The user permission must be inserted
     * by liquibase scripts.
     * 
     * <p>The test also checks functionality of {@link AccountRepositoryImpl#getPermissionsForAccount(org.panifex.service.api.security.Account)}
     * method.
     */
    @Test
    public void getPermissionsForAccountTest() {
        // get admin account
        AccountEntity adminAccount = accountRepository.getAccountByUsername(entityManager, ADMIN_USERNAME);
        
        // must be admin
        assertEquals(ADMIN_USERNAME, adminAccount.getUsername());
        
        // get admin's permissions
        List<? extends Permission> permissions = 
                accountRepository.getPermissionsForAccount(entityManager, adminAccount);
        
        // check if admin user has user permission
        assertNotNull(permissions);
        assertEquals(USER_PERMISSION, permissions.get(0).getWildcardExpression());
    }

    

}
