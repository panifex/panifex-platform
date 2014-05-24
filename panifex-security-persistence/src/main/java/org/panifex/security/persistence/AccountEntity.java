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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.panifex.persistence.spi.AbstractEntity;
import org.panifex.service.api.security.Account;

@Entity
@StaticMetamodel(AccountEntity_.class)
@Table(name = "sec_account")
public class AccountEntity extends AbstractEntity implements Account, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 6039053761668790089L;

    private String username;
    private String password;
    private String passwordSalt;
    private boolean isCredentialsExpired;

    private List<AccountRoleAssociationEntity> accountRoleAssociations;

    public AccountEntity(
            Long id,
            int optlockVersion,
            String username,
            String password,
            String passwordSalt) {
        super(id, optlockVersion);
        this.username = username;
        this.password = password;
        this.passwordSalt = passwordSalt;
    }

    public AccountEntity(
            String username,
            String password,
            String passwordSalt) {
        this.username = username;
        this.password = password;
        this.passwordSalt = passwordSalt;
    }

    protected AccountEntity() {
    }

    @Column(name = "username", nullable = false, unique = true)
    @Override
    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "password_salt", nullable = false)
    public String getPasswordSalt() {
        return passwordSalt;
    }

    protected void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Column(name = "is_credentials_expired", nullable = false)
    @Override
    public boolean getIsCredentialsExpired() {
        return isCredentialsExpired;
    }

    protected void setIsCredentialsExpired(boolean isCredentialsExpired) {
        this.isCredentialsExpired = isCredentialsExpired;
    }

    @OneToMany(mappedBy = "account")
    protected List<AccountRoleAssociationEntity> getAccountRoleAssociations() {
        return accountRoleAssociations;
    }

    protected void setAccountRoleAssociations(List<AccountRoleAssociationEntity> accountRoleAssociations) {
        this.accountRoleAssociations = accountRoleAssociations;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                appendSuper(super.hashCode()).
                append(username).
                append(password).
                append(passwordSalt).
                append(isCredentialsExpired).
                append(accountRoleAssociations).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AccountEntity other = (AccountEntity) obj;
        return new EqualsBuilder().
                appendSuper(super.equals(obj)).
                append(username, other.username).
                append(password, other.password).
                append(passwordSalt, other.passwordSalt).
                append(isCredentialsExpired, other.isCredentialsExpired).
                append(accountRoleAssociations, other.accountRoleAssociations).
                isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                appendSuper(super.toString()).
                append("username", username).
                append("password", password).
                append("passwordSalt", passwordSalt).
                append("isCredentialsExpired", isCredentialsExpired).
                append("accountRoleAssociations", accountRoleAssociations).
                toString();
    }
}
