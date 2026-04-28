package com.loanapp.service;

import com.loanapp.dto.RegisterRequest;
import com.loanapp.dto.UpdateUserRequest;
import com.loanapp.entity.User;

import java.util.List;

public interface UserService {

    User registerUser(RegisterRequest request);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}