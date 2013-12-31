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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Service;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.panifex.service.api.security.AccountNotExpiredException;
import org.panifex.service.api.security.AccountNotFoundException;
import org.panifex.service.api.security.Permission;
import org.panifex.service.api.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Realm that allows authentication and authorization via persisted data.
 * 
 */
@Bean(id = PersistenceRealm.ID)
@Service(interfaces = SecurityService.class)
@ReferenceListener
public class PersistenceRealm extends AuthorizingRealm implements SecurityService {

    private final Logger log = LoggerFactory.getLogger(PersistenceRealm.class);

    public static final String ID = "org.panifex.web.impl.security.OsgiRealm";
    
    /**
     * Hash algorithm name to use when performing hashes for credentials matching.
     */
    public static final String HASH_ALGORITHM = Sha512Hash.ALGORITHM_NAME;
    
    /**
     * The number of times a submitted {@code AuthenticationToken}'s credentials will be hashed before comparing
     * to the credentials stored in the system.
     */
    public static final int HASH_ITERATIONS = 1024;
    
    @Inject(ref = AccountRepositoryImpl.ID)
    private AccountRepositoryImpl accountRepository;
    
    /**
     * {@link org.apache.shiro.crypto.RandomNumberGenerator RandomNumberGenerator} is used
     * for creating random numbers for generating password salts.
     */
    private RandomNumberGenerator randomGenerator = new SecureRandomNumberGenerator();
    
    private EntityManager entityManager;
    
    /**
     * Constructor adds EhCacheManager.
     */
    public PersistenceRealm() {
        super(
            new EhCacheManager());
        
        // set hashed credentials matcher
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        credentialsMatcher.setHashIterations(HASH_ITERATIONS);
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        setCredentialsMatcher(credentialsMatcher);
    }
    
    public void setAccountRepository(AccountRepositoryImpl accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        log.debug("Get authentication info for username: {}", username);

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        // get account from repository
        AccountEntity account = accountRepository.getAccountByUsername(entityManager, username);
        
        if (account != null) {
            // get password
            String password = account.getPassword();
            
            // get password salt
            String passwordSalt = account.getPasswordSalt();
    
            // check user's username and password
            if (password == null || passwordSalt == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("No account found for user ");
                sb.append(username);
                throw new UnknownAccountException(sb.toString());
            }
            
            // check if user's account is expired
            if (account.getIsCredentialsExpired()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Credentials has been expired for user ");
                sb.append(username);
                throw new ExpiredCredentialsException(sb.toString());
            }
    
            SimpleAuthenticationInfo info =
                    new SimpleAuthenticationInfo(
                        username, 
                        password,
                        ByteSource.Util.bytes(Base64.decode(passwordSalt)),
                        getName());
    
            log.debug("Authentication info resolved: username={}", username);
    
            return info;
        } else {
            // account == null
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        log.debug("Get authorization info for username: {}", username);
        
        // get account from repository
        AccountEntity account = accountRepository.getAccountByUsername(entityManager, username);
       
        // create empty sets
        Set<String> roleNames = new HashSet<>();
        Set<String> permissionWildcardExpressions = new HashSet<>();
        
        if (account != null ) {
            List<? extends Permission> permissions = 
                    accountRepository.getPermissionsForAccount(entityManager, account);
            
            for (Permission permission : permissions) {
                String wildcardExpression = permission.getWildcardExpression();
                permissionWildcardExpressions.add(wildcardExpression);
            }
        } 
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissionWildcardExpressions);
        return info;
    }

    @Override
    public void updateAccountExpiredPassword(String username, String plainPassword) 
            throws AccountNotFoundException, AccountNotExpiredException {
        
        AccountEntity account = accountRepository.getAccountByUsername(entityManager, username);
        
        if (account == null) {
            throw new AccountNotFoundException();
        }
        
        if (!account.getIsCredentialsExpired()) {
            throw new AccountNotExpiredException();
        }
        
        String passwordSalt = getRandomPasswordSalt();
        String password = getHashedPasswordBase64(plainPassword, passwordSalt);
        
        account.setPassword(password);
        account.setPasswordSalt(passwordSalt);
        account.setIsCredentialsExpired(false);
        
        accountRepository.updateAccount(entityManager, account);
    }

    
    protected String getRandomPasswordSalt() {
        return randomGenerator.nextBytes().toBase64();
    }
    
    protected String getHashedPasswordBase64(String plainTextPassword, String passwordSalt) {
        // decode password salt
        byte[] salt = Base64.decode(passwordSalt);
            
        // hash salted password
        String hashedPasswordBase64 = new SimpleHash(
            PersistenceRealm.HASH_ALGORITHM, 
            plainTextPassword, 
            salt, 
            PersistenceRealm.HASH_ITERATIONS).toBase64();
        
        return hashedPasswordBase64;
    }
}
