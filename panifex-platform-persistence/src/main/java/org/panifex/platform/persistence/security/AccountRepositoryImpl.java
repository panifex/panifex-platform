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
import org.apache.aries.blueprint.annotation.Service;
import org.apache.aries.blueprint.annotation.Unbind;
import org.apache.aries.transaction.annotations.Transaction;
import org.panifex.platform.api.security.Account;
import org.panifex.platform.api.security.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = "org.panifex.platform.persistence.security.AccountRepositoryImpl")
@ReferenceListener
@Service(interfaces = AccountRepository.class)
public class AccountRepositoryImpl implements AccountRepository {

    private Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    @Inject
    @Reference(serviceInterface = EntityManagerFactory.class, filter = "(osgi.unit.name=panifex-cm)", referenceListeners = @ReferenceListener(ref = "org.panifex.platform.persistence.security.AccountRepositoryImpl"))
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
        log.debug("Unbind entityManager factory: {}", entityManagerFactory);
        this.entityManagerFactory = null;
        this.entityManager = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transaction
    public void insertAccount(Account account) {
        entityManager.persist(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getAccountByUsername(String username) {
        try {
            log.debug("Get account with username: {}", username);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AccountImpl> cq = cb.createQuery(AccountImpl.class);
            Root<AccountImpl> account = cq.from(AccountImpl.class);
            cq.select(account);
            TypedQuery<AccountImpl> q = entityManager.createQuery(cq);
            List<AccountImpl> allAccounts = q.getResultList();
            if (allAccounts.size() == 1) {
                log.info("Founded account with username: {}", username);
                return allAccounts.get(0);
            } else if (allAccounts.size() == 0) {
                log.info("Account with username: {} hasn't found", username);
                return null;
            } else {
                log.error("More than one account with same username: {}", username);
                throw new RuntimeException("More than one account with same username");
            }
        } catch (Exception e) {
            log.error("Unable to get account: {}", e);
            throw e;
        }
    }

}
