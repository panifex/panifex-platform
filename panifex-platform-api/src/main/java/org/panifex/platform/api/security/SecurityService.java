package org.panifex.platform.api.security;

import java.util.Collection;
import java.util.Set;

public interface SecurityService {

    String[] getPasswordForUser(String username);

    Set<String> getRoleNamesForUser(String username);

    Set<String> getPermissions(String username, Collection<String> roleNames);
}
