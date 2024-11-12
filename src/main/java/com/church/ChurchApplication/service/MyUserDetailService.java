package com.church.ChurchApplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.church.ChurchApplication.entity.*;
import com.church.ChurchApplication.entity.Ulogin;
import com.church.ChurchApplication.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Ulogin user = repo.findUserByEmail(email);
		if(user==null)
		{
			throw new UsernameNotFoundException("User 404", null);
		}
		return new UserPrincipal(user);
	}

}
