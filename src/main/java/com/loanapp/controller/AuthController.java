package com.loanapp.controller;

import com.loanapp.dto.LoginRequest;
import com.loanapp.dto.RegisterRequest;
import com.loanapp.entity.User;
import com.loanapp.security.JwtUtil;
import com.loanapp.security.CustomUserDetails;
import com.loanapp.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        User savedUser = userService.registerUser(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("email", savedUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ LOGIN (UPDATED WITH ROLE + USER ID IN TOKEN)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // 🔥 Extract authenticated user details
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String email = userDetails.getUsername();
            String role = userDetails.getUser().getRole().name();
            Long userId = userDetails.getUser().getId();

            // 🔥 Generate enhanced JWT
            String token = jwtUtil.generateToken(email, role, userId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("role", role);     // optional but useful for frontend
            response.put("userId", userId); // optional

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }
}