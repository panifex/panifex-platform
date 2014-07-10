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
package org.panifex.security.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.panifex.module.api.accounts.Permission;
import org.panifex.module.api.accounts.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountRepositoryImpl implements AccountRepository<AccountEntity> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public void insertAccount(EntityManager entityManager, AccountEntity account) {
        log.debug("Insert account: {}", account);
        /*
        AccountImplBuilder accountBuilder = new AccountImplBuilder(account);
        
        AccountEntity accountImpl = accountBuilder.build();
        
        entityManager.persist(accountImpl);*/
    }
    
    @Override
    public void updateAccount(EntityManager entityManager, AccountEntity account) {
        log.debug("Update account: {}", account);
        entityManager.persist(account);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AccountEntity getAccountByUsername(EntityManager entityManager, String username) {
        log.debug("Get account with username: {}", username);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountEntity> cq = cb.createQuery(AccountEntity.class);
        
        // select from account
        Root<AccountEntity> account = cq.from(AccountEntity.class);
        // where account.username = ?
        cq.where(cb.equal(account.get(AccountEntity_.username), username));
        cq.select(account);
        
        // execute query
        TypedQuery<AccountEntity> q = entityManager.createQuery(cq);
        List<AccountEntity> accounts = q.getResultList();
        
        if (accounts.size() == 1) {
            log.debug("Found account with username: {}", username);
            return accounts.get(0);
        } else if (accounts.size() == 0) {
            log.debug("Account with username: {} has not been found", username);
            return null;
        } else {
            // accounts.size() > 1
            log.error("Found more than one account with the same username: {}", username);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Role> getRolesForAccount(EntityManager entityManager, AccountEntity account) {
        log.debug("Get roles by account: {}", account);
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> cq = cb.createQuery(RoleEntity.class);
        
        // select from role
        Root<RoleEntity> role = cq.from(RoleEntity.class);
        cq.from(AccountRoleAssociationEntity.class);
        cq.from(AccountEntity.class);
        
        // join account
        Join<RoleEntity, AccountRoleAssociationEntity> accountRoleAssociation =
                role.join(RoleEntity_.accountRoleAssociations);
        Join<AccountRoleAssociationEntity, AccountEntity> accounts = 
                accountRoleAssociation.join(AccountRoleAssociationEntity_.account);
        
        // where account = ?
        cq.where(cb.equal(accounts.get(AccountEntity_.id), account.getId()));
        
        // distinct
        cq.distinct(true);
        cq.select(role);
        
        // execute query
        TypedQuery<RoleEntity> q = entityManager.createQuery(cq);
        List<RoleEntity> roles = q.getResultList();
        
        return roles;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Permission> getPermissionsForAccount(EntityManager entityManager, AccountEntity account) {
        log.debug("Get permission by account: {}", account);
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PermissionEntity> cq = cb.createQuery(PermissionEntity.class);
        
        // select from permission
        Root<PermissionEntity> permission = cq.from(PermissionEntity.class);
        
        // loads metamodel
        cq.from(AccountRoleAssociationEntity.class);
        cq.from(RolePermissionAssociationEntity.class);
        cq.from(RoleEntity.class);
        cq.from(AccountEntity.class);
        
        // join role
        Join<PermissionEntity, RolePermissionAssociationEntity> rolePermission = 
                permission.join(PermissionEntity_.rolePermissionAssociations);
        Join<RolePermissionAssociationEntity, RoleEntity> roles = 
                rolePermission.join(RolePermissionAssociationEntity_.role);
        Join<RoleEntity, AccountRoleAssociationEntity> accountRoleAssociation = 
                roles.join(RoleEntity_.accountRoleAssociations);
        Join<AccountRoleAssociationEntity, AccountEntity> accounts =
                accountRoleAssociation.join(AccountRoleAssociationEntity_.account);
               
        // where
        cq.where(cb.equal(accounts.get(AccountEntity_.id), account.getId()));
        
        // distinct
        cq.select(permission);
        cq.distinct(true);
        
        // execute query
        TypedQuery<PermissionEntity> q = entityManager.createQuery(cq);
        List<PermissionEntity> permissions = q.getResultList();
        
        log.debug("Returned {} permissions", permissions.size());
        
        return permissions;
    }

}
