package com.springBoot.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.springBoot.security.model.User;


public interface UserService extends UserDetailsService{
	
	User save(User user);

    User findByEmail(String email);
	
}