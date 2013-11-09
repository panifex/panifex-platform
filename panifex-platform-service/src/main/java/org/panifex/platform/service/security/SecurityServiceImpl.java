package org.panifex.platform.service.security;

import java.util.Collection;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Service;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.platform.api.security.Account;
import org.panifex.platform.api.security.AccountRepository;
import org.panifex.platform.api.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = "org.panifex.platform.service.security.SecurityServiceImpl")
@ReferenceListener
@Service(interfaces = SecurityService.class)
public class SecurityServiceImpl implements SecurityService {

	private Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	@Inject
	@Reference(serviceInterface = AccountRepository.class, referenceListeners = @ReferenceListener(ref = "org.panifex.platform.service.security.SecurityServiceImpl"))
	private AccountRepository accountRepository;
	
	@Bind
	public void bind(AccountRepository accountRepository) {
		log.debug("Bind account repository: {}", accountRepository);
		this.accountRepository = accountRepository;
	}
	
	@Unbind
	public void unbind(AccountRepository accountRepository) {
		log.debug("Unbind account repository: {}", accountRepository);
		this.accountRepository = null;
	}
	
	@Override
	public String[] getPasswordForUser(String username) {
		Account account = accountRepository.getAccountByUsername(username);
		if (account != null) {
			return new String[]{account.getPassword()};
		} else {
			return new String[0];
		}
	}

	@Override
	public Set<String> getPermissions(String arg0, Collection<String> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getRoleNamesForUser(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
