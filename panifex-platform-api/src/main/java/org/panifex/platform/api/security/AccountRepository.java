package org.panifex.platform.api.security;

public interface AccountRepository {

    void insertAccount(Account account);

    /**
     * Gets account by account's username.
     * 
     * @param username Account's username
     * @return Account if it is exist, or null if is isn't
     */
    Account getAccountByUsername(String username);
}
