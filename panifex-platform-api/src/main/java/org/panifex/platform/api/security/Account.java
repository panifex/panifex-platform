package org.panifex.platform.api.security;

import org.panifex.platform.api.Entity;

public interface Account extends Entity {

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);
}
