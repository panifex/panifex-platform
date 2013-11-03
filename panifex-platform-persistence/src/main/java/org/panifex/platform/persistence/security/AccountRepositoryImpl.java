package org.panifex.platform.persistence.security;

import javax.persistence.EntityManager;

import org.panifex.platform.api.security.Account;
import org.panifex.platform.api.security.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

	private EntityManager entityManager;
	
    public void setEntityManager (EntityManager em) { 
        entityManager = em;
    }
	
	@Override
	public void insertAccount(Account account) {
		entityManager.persist(account);
	}

}
