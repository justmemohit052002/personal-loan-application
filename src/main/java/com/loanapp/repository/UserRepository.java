package com.loanapp.repository;

import com.loanapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔐 Only active user by email
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    // 🔐 Only active user by ID (FIXED with @Param)
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isDeleted = false")
    Optional<User> findActiveUserById(@Param("id") Long id);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    // ✅ Only active users
    List<User> findByIsDeletedFalse();
}