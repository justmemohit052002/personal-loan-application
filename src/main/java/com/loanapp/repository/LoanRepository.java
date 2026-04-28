package com.loanapp.repository;

import com.loanapp.entity.Loan;
import com.loanapp.enums.LoanStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);
    
    List<Loan> findByStatus(LoanStatus status);
}