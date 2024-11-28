package com.church.ChurchApplication.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	
	private Ulogin ulogin;
	
	public UserPrincipal(Ulogin ulogin)
	{
		this.ulogin=ulogin;
	}

	public Ulogin getUser() {
		return ulogin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		
		return ulogin.getPassword();
	}

	@Override
	public String getUsername() {
		return ulogin.getEmail();
	}

}
