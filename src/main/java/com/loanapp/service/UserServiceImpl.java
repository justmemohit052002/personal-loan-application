package com.loanapp.service;


import com.loanapp.entity.User;
import com.loanapp.enums.Role;
import com.loanapp.repository.UserRepository;
import com.loanapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    // ✅ REGISTER USER (AUTH CORE LOGIC)
    @Override
    public User registerUser(User user) {

        // 🔍 VALIDATION
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (user.getMobileNumber() != null &&
            userRepository.existsByMobileNumber(user.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }

        // 🔐 PASSWORD ENCODING
        user.setPassword(encoder.encode(user.getPassword()));

        // 🎭 ROLE ASSIGNMENT
        if (user.getRole() == null) {
            user.setRole(Role.USER); // default role
        }

        // 🧾 SAVE USER
        return userRepository.save(user);
    }

    // ✅ GET BY EMAIL (used for future logic)
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ✅ UPDATE USER
    @Override
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setMobileNumber(updatedUser.getMobileNumber());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setState(updatedUser.getState());
        existingUser.setPincode(updatedUser.getPincode());

        // 🔐 update password only if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    // ✅ GET BY ID
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ GET ALL
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(u -> !Boolean.TRUE.equals(u.getIsDeleted()))
                .toList();
    }

    // ✅ DELETE (SOFT DELETE)
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsDeleted(true);
        userRepository.save(user);
    }
}