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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.metamodel.StaticMetamodel;

import org.panifex.platform.api.security.Account;

@Entity()
@StaticMetamodel(AccountImpl_.class)
@Table(name = "sec_account")
public class AccountImpl implements Account, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 6039053761668790089L;

    @Id
    @Column(name = "account_id", nullable = false)
    private Long id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;

    @Column(name = "password_salt")
    private String passwordSalt;
    
    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }
    
    public String getPasswordSalt() {
        return passwordSalt;
    }
    
    protected void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}
