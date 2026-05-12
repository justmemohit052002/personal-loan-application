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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // ============================
    // REGISTER USER
    // ============================

    @Override
    @Transactional
    public User registerUser(RegisterRequest request) {

        String email =
                request.getEmail()
                        .toLowerCase()
                        .trim();

        // 🔥 FIXED
        if (userRepository
                .existsByEmailAndIsDeletedFalse(email)) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        // 🔥 FIXED
        if (userRepository
                .existsByMobileNumberAndIsDeletedFalse(
                        request.getMobileNumber()
                )) {

            throw new RuntimeException(
                    "Mobile number already exists"
            );
        }

        User user = new User();

        user.setFullName(request.getFullName());

        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setMobileNumber(
                request.getMobileNumber()
        );

        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setAddress(request.getAddress());

        user.setRole(Role.USER);

        User savedUser =
                userRepository.save(user);

        // 📧 SEND WELCOME EMAIL
        emailService.sendRegistrationEmail(
                savedUser.getEmail(),
                savedUser.getFullName()
        );

        return savedUser;
    }

    // ============================
    // GET USER BY ID
    // ============================

    @Override
    public User getUserById(Long id) {

        return userRepository.findActiveUserById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        ));
    }

    // ============================
    // GET ALL USERS
    // ============================

    @Override
    public List<User> getAllUsers() {

        return userRepository.findByIsDeletedFalse();
    }

    // ============================
    // UPDATE USER
    // ============================

    @Override
    @Transactional
    public User updateUser(Long id,
                           UpdateUserRequest request) {

        User user = getUserById(id);

        // ============================
        // UPDATE FULL NAME
        // ============================

        if (request.getFullName() != null &&
                !request.getFullName().isBlank()) {

            user.setFullName(
                    request.getFullName().trim()
            );
        }

        // ============================
        // UPDATE MOBILE NUMBER
        // ============================

        if (request.getMobileNumber() != null &&
                !request.getMobileNumber().isBlank()) {

            String newMobile =
                    request.getMobileNumber().trim();

            if (!newMobile.equals(user.getMobileNumber())
                    &&
                    userRepository
                            .existsByMobileNumberAndIsDeletedFalse(
                                    newMobile
                            )) {

                throw new RuntimeException(
                        "Mobile number already exists"
                );
            }

            user.setMobileNumber(newMobile);
        }

        // ============================
        // UPDATE CITY
        // ============================

        if (request.getCity() != null &&
                !request.getCity().isBlank()) {

            user.setCity(
                    request.getCity().trim()
            );
        }

        // ============================
        // UPDATE STATE
        // ============================

        if (request.getState() != null &&
                !request.getState().isBlank()) {

            user.setState(
                    request.getState().trim()
            );
        }

        // ============================
        // UPDATE ADDRESS
        // ============================

        if (request.getAddress() != null &&
                !request.getAddress().isBlank()) {

            user.setAddress(
                    request.getAddress().trim()
            );
        }

        return userRepository.save(user);
    }

    // ============================
    // SOFT DELETE USER
    // ============================

    @Override
    @Transactional
    public void deleteUser(Long id) {

        User user = getUserById(id);

        // Already deleted check
        if (Boolean.TRUE.equals(
                user.getIsDeleted()
        )) {

            throw new RuntimeException(
                    "User already deleted"
            );
        }

        user.setIsDeleted(true);

        userRepository.save(user);
    }
}