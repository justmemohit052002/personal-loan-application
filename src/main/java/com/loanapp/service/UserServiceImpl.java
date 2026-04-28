package com.loanapp.service;

import com.loanapp.dto.RegisterRequest;
import com.loanapp.dto.UpdateUserRequest;
import com.loanapp.entity.User;
import com.loanapp.enums.Role;
import com.loanapp.exception.UserNotFoundException;
import com.loanapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    @Override
    public User registerUser(RegisterRequest request) {

        String email = request.getEmail().toLowerCase().trim();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobileNumber(request.getMobileNumber());

        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setAddress(request.getAddress());

        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    // 🔥 GET USER (SAFE)
    @Override
    public User getUserById(Long id) {

        return userRepository.findActiveUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    // 🔥 GET ALL USERS (SAFE)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    // 🔥 UPDATE USER (SAFE)
    @Override
    public User updateUser(Long id, UpdateUserRequest request) {

        User user = getUserById(id);

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        if (request.getMobileNumber() != null && !request.getMobileNumber().isBlank()) {

            if (!request.getMobileNumber().equals(user.getMobileNumber()) &&
                    userRepository.existsByMobileNumber(request.getMobileNumber())) {

                throw new RuntimeException("Mobile number already exists");
            }

            user.setMobileNumber(request.getMobileNumber());
        }

        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getState() != null) user.setState(request.getState());
        if (request.getAddress() != null) user.setAddress(request.getAddress());

        return userRepository.save(user);
    }

    // 🔥 SOFT DELETE
    @Override
    public void deleteUser(Long id) {

        User user = getUserById(id);

        user.setIsDeleted(true);

        userRepository.save(user);
    }
}