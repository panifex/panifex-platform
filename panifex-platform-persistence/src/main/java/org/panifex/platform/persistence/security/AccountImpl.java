package org.panifex.platform.persistence.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.panifex.platform.api.security.Account;

@Entity(name = "account")
@Table(name = "account")
public class AccountImpl implements Account, Serializable {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 6039053761668790089L;
	
	@Id
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	
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


	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
}
