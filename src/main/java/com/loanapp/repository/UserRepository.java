package com.loanapp.repository;

import com.loanapp.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔐 Used in login (VERY IMPORTANT)
    Optional<User> findByEmail(String email);

    // 📱 Optional: login via mobile (future use)
    Optional<User> findByMobileNumber(String mobileNumber);

    // 🔍 Check duplicates during registration
    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);
}
