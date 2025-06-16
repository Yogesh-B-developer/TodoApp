package com.example.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.JwtFilter;
import com.example.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.cors().and()
		.csrf(csrf->csrf.disable())		
		.authorizeHttpRequests(
                     auth -> auth.requestMatchers(
                    		 "user/**").authenticated()
                     .requestMatchers("/").permitAll()
                     .anyRequest().permitAll()
                     )
//		             .formLogin(form-> form.permitAll()
//		             .defaultSuccessUrl("/"))
		             .sessionManagement(session -> 
		                                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		
//		UserDetails user = User.withUsername("alice")
//				           .password(passwordEncoder.encode("user123"))
//				           .roles("user")
//				           .build();
//		
//		UserDetails admin = User.withUsername("zack")
//		           .password(passwordEncoder.encode("admin123"))
//		           .roles("admin")
//		           .build();
//		
//		return new InMemoryUserDetailsManager(user,admin);
		
		return new CustomUserDetailsService();
		
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(authenticationProvider()));
	}
}
