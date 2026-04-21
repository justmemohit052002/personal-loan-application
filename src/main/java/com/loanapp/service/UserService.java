package com.loanapp.service;

 
import com.loanapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Auth-related (business side)
    User registerUser(User user);

    Optional<User> getUserByEmail(String email);

    // General CRUD
    User updateUser(Long id, User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);
}
