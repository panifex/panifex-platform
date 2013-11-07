package org.panifex.platform.service.security;

import java.util.Collection;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.panifex.platform.api.security.SecurityService;

@Bean(id = "org.panifex.platform.service.security.SecurityServiceImpl")
@Service(interfaces = SecurityService.class)
public class SecurityServiceImpl implements SecurityService {

	@Override
	public String[] getPasswordForUser(String username) {
		return new String[]{"1234"};
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
