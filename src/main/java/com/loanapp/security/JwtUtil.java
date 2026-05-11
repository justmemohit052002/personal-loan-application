package com.loanapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // ============================
    // 🔐 SECRET KEY
    // ============================
    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ============================
    // 🔥 GENERATE TOKEN
    // ============================
    public String generateToken(String email, String role, Long userId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    // ============================
    // 🔥 EXTRACT USERNAME
    // ============================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ============================
    // 🔥 EXTRACT EXPIRATION
    // ============================
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // ============================
    // 🔥 CHECK TOKEN EXPIRY
    // ============================
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ============================
    // 🔥 EXTRACT CLAIMS
    // ============================
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ============================
    // 🔥 VALIDATE TOKEN
    // ============================
    public boolean validateToken(String token, String email) {

        try {

            final String username = extractUsername(token);

            return username.equals(email)
                    && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}