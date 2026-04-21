package com.loanapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	private final long EXPIRATION = 3600000;

	private final String SECRET = "this_is_a_super_secure_secret_key_123456";

	private Key getKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(getKey()).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token, String email) {
		return extractUsername(token).equals(email);
	}
}