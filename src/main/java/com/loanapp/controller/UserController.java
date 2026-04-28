package com.loanapp.controller;

import com.loanapp.dto.UpdateUserRequest;
import com.loanapp.dto.UserResponse;
import com.loanapp.entity.User;
import com.loanapp.mapper.UserMapper;
import com.loanapp.security.CustomUserDetails;
import com.loanapp.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ GET logged-in user profile
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        User user = userDetails.getUser();

        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    // ✅ GET user by ID (ADMIN + LOAN_OFFICER)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LOAN_OFFICER')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    // ✅ UPDATE profile (SAFE)
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        Long id = userDetails.getUser().getId();
        User updated = userService.updateUser(id, request);

        return ResponseEntity.ok(Map.of(
                "message", "Profile updated successfully",
                "fullName", updated.getFullName(),
                "email", updated.getEmail()
        ));
    }

    // 🔥 GET ALL USERS (ADMIN + LOAN_OFFICER)
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','LOAN_OFFICER')")
    public ResponseEntity<?> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(UserMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    // ✅ DELETE USER (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(Map.of(
                "message", "User deleted successfully"
        ));
    }
}