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
package org.panifex.platform.persistence.security;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.platform.api.security.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = AccountRepositoryImpl.ID)
@ReferenceListener
public class AccountRepositoryImpl implements AccountRepository {

    public static final String ID = "org.panifex.platform.persistence.security.AccountRepository";
    
    private Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);
    
    @Inject
    @Reference(
        serviceInterface = EntityManagerFactory.class, 
        filter = "(osgi.unit.name=panifex-cm)", 
        referenceListeners = @ReferenceListener(ref = ID))
    private EntityManagerFactory entityManagerFactory;
    
    private EntityManager entityManager;

    @Bind
    public void bind(EntityManagerFactory entityManagerFactory) {
        log.debug("Bind entity manager factory: {}", entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;

        // create entity manager
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Unbind
    public void unbind(EntityManagerFactory entityManagerFactory) {
        log.debug("Unbind entity manager factory: {}", entityManagerFactory);
        this.entityManagerFactory = null;
        this.entityManager = null;
    }

    @Override
    public void insertAccount(Account account) {
        log.debug("Insert account: {}", account);
        AccountImplBuilder accountBuilder = new AccountImplBuilder(account);
        
        AccountImpl accountImpl = accountBuilder.build();
        
        entityManager.persist(accountImpl);
    }
    
    @Override
    public AccountImpl getAccountByUsername(String username) {
        log.debug("Get account with username: {}", username);
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountImpl> cq = cb.createQuery(AccountImpl.class);
        
        // select from account
        Root<AccountImpl> account = cq.from(AccountImpl.class);
        // where username = ?
        cq.where(cb.equal(account.get(AccountImpl_.username), username));
        cq.select(account);
        
        TypedQuery<AccountImpl> q = entityManager.createQuery(cq);
        List<AccountImpl> accounts = q.getResultList();
        
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

}
