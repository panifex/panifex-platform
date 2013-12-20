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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.panifex.platform.api.security.Account;

@Entity
@StaticMetamodel(AccountEntity_.class)
@Table(name = "sec_account")
public class AccountEntity implements Account, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 6039053761668790089L;

    private Long id;
    
    private String username;
    
    private String password;

    private String passwordSalt;
    
    private List<RoleEntity> roles;
    
    public AccountEntity(
            String username,
            String password,
            String passwordSalt) {
        this.username = username;
        this.password = password;
        this.passwordSalt = passwordSalt;
    }
    
    @Id
    @Column(name = "account_id", nullable = false)
    @Override
    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
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

    protected void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name = "password_salt", nullable = false)
    public String getPasswordSalt() {
        return passwordSalt;
    }
    
    protected void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    
    @ManyToMany
    @JoinTable(name = "sec_account_role",
    joinColumns = {@JoinColumn(name = "account_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    protected List<RoleEntity> getRoles() {
        return roles;
    }
    
    protected void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
