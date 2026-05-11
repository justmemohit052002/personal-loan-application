// UserRepository.java

package com.loanapp.repository;

import com.loanapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isDeleted = false")
    Optional<User> findActiveUserById(@Param("id") Long id);

    boolean existsByEmailAndIsDeletedFalse(String email);

    boolean existsByMobileNumberAndIsDeletedFalse(String mobileNumber);

    List<User> findByIsDeletedFalse();
}