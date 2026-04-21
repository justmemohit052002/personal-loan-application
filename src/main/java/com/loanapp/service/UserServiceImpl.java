package com.loanapp.service;

import com.loanapp.entity.User;
import com.loanapp.enums.Role;
import com.loanapp.repository.UserRepository;

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

    // ✅ REGISTER USER
    @Override
    public User registerUser(User user) {

        // 🔤 Normalize email
        String email = user.getEmail().toLowerCase().trim();
        user.setEmail(email);

        // 🔍 VALIDATION
        if (email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (user.getMobileNumber() != null &&
            userRepository.existsByMobileNumber(user.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number already exists");
        }

        // PASSWORD ENCODING
        user.setPassword(encoder.encode(user.getPassword()));

        //  FORCE ROLE (prevent frontend hacking)
        user.setRole(Role.USER);

        // DEFAULT FLAG
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    // ✅ GET BY EMAIL
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase().trim());
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

        // update password only if provided
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

    // ✅ GET ALL (only active users)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findByIsDeletedFalse(); // better than filtering in memory
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