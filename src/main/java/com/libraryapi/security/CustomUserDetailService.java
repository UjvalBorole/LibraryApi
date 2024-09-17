package com.libraryapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.libraryapi.entities.User;
import com.libraryapi.exceptions.ResourceNotFoundException;
import com.libraryapi.repository.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		//loading user from database by username 
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User "," Email "+username,0));
		
		return user;
	}
}
