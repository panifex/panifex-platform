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
package org.panifex.persistence.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@IdClass(AccountRoleAssociationId.class)
@StaticMetamodel(AccountRoleAssociationEntity_.class)
@Table(name = "sec_account_role")
public class AccountRoleAssociationEntity {

    private int accountId;
    private int roleId;

    private AccountEntity account;
    private RoleEntity role;

    @Id
    @Column(name = "account_id")
    protected int getAccountId() {
        return accountId;
    }
    
    protected void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    @Id
    @Column(name = "role_id")
    protected int getRoleId() {
        return roleId;
    }
    
    protected void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    @ManyToOne
    public AccountEntity getAccount() {
        return account;
    }
    
    protected void setAccount(AccountEntity account) {
        this.account = account;
    }
    
    @ManyToOne
    public RoleEntity getRole() {
        return role;
    }
    
    protected void setRole(RoleEntity role) {
        this.role = role;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 7).
                append(accountId).
                append(roleId).
                append(account).
                append(role).
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
        AccountRoleAssociationEntity other = (AccountRoleAssociationEntity) obj;
        return new EqualsBuilder().
                append(accountId, other.accountId).
                append(roleId, other.roleId).
                append(account, other.account).
                append(role, other.role).
                isEquals();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("accountId", accountId).
                append("roleId", roleId).
                append("account", account).
                append("role", role).
                toString();
    }
}
