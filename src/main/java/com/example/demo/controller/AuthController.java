package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil ;
	
	@PostMapping("/register")
	public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity userEntity) {
		
		Optional<UserEntity> existingUser = userRepository.findById(userEntity.getEmail());
		if(existingUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		
		UserEntity user =  userRepository.save(userEntity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {
	   // authenticate the user
		
	   // authentication manager user to validate user credentials, returns authentication object if success
		
		System.out.println("called");
		
		try {
			
		
	Authentication authentication =	authManager.authenticate(
			              new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
	
	      UserDetails  userDetails =(UserDetails)  authentication.getPrincipal();
	      
	      String token = jwtUtil.generateToken(userDetails);
	      
	      Optional<UserEntity> userEntity = userRepository.findById(loginRequest.getEmail());
	        String name = userEntity.map(UserEntity::getName).orElse("Unknown");
	       
	      return ResponseEntity.ok(Map.of("token",token,"name",name,"email",loginRequest.getEmail()));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid user name or password"));
		}
	       
	}
}
