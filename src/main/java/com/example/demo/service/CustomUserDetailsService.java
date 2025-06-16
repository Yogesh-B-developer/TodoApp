package com.example.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// ftech user from data base
		UserEntity userEntity = userRepository.findById(email)
				                .orElseThrow(()-> new CustomException("user not found"));
		return new User(userEntity.getEmail(),userEntity.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole())));
	}

}
