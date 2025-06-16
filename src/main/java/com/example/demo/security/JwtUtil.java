package com.example.demo.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// for generating jwt token

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY_STRING ="XweG6O37d6t9E2BOb19NQfzNrsHCUqjK"; 

	private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());  
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				   .subject(userDetails.getUsername())
				   .issuedAt(new Date())
				   .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 60))
				   .signWith(SECRET_KEY,Jwts.SIG.HS256)
				   .compact();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
	   return extractUserName(token).equals(userDetails.getUsername());
	}
	public String extractUserName(String token) {
		
		return Jwts.parser()
		    .verifyWith(SECRET_KEY)
		    .build()
		    .parseSignedClaims(token) // for decrypting details from token 
		    .getPayload() // to get details for which we gave to token
		    .getSubject();
	}
}
