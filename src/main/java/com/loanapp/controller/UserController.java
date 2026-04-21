package com.loanapp.controller;

import com.loanapp.dto.UserResponse;
import com.loanapp.entity.User;
import com.loanapp.security.CustomUserDetails;
import com.loanapp.service.UserService;

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

        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole(),
                user.getCity(),
                user.getState()
        ));
    }

    // ✅ GET user by ID (Admin only)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole(),
                user.getCity(),
                user.getState()
        ));
    }

    // ✅ UPDATE profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody User updatedUser) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        Long id = userDetails.getUser().getId();
        User updated = userService.updateUser(id, updatedUser);

        return ResponseEntity.ok(Map.of(
                "message", "Profile updated successfully",
                "fullName", updated.getFullName(),
                "email", updated.getEmail()
        ));
    }

    // 🔥 GET ALL USERS (Admin only, SAFE DATA)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getFullName(),
                        u.getEmail(),
                        u.getMobileNumber(),
                        u.getRole(),
                        u.getCity(),
                        u.getState()
                ))
                .toList();

        return ResponseEntity.ok(users);
    }

    // ✅ DELETE USER (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(Map.of(
                "message", "User deleted successfully"
        ));
    }
}