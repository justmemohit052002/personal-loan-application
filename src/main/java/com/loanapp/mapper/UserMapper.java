package com.loanapp.mapper;

import com.loanapp.dto.UserResponse;
import com.loanapp.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole(),
                user.getCity(),
                user.getState()
        );
    }
}