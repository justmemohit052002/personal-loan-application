package com.loanapp.service;

import com.loanapp.dto.LoanRequest;
import com.loanapp.entity.Loan;

import java.util.List;

public interface LoanService {

    // ============================
    // TASK 1
    // ============================
    Loan applyLoan(Long userId, LoanRequest request);

    List<Loan> getUserLoans(Long userId);

    Loan getLoanById(Long id, Long userId);

    // ============================
    // TASK 2 (LOAN OFFICER)
    // ============================
    List<Loan> getPendingLoans();

    Loan approveLoan(Long loanId);

    Loan rejectLoan(Long loanId);

    // ============================
    // 🥉 TASK 3 (ADMIN)
    // ============================
    List<Loan> getAllLoans();

    Loan updateLoanStatus(Long id, String status);
    // ============================
    // 🥉 TASK 4 (LOAN OFFICER - AUTOMATED DECISION)/New feature/07/05
    Loan finalDecision(Long loanId);
}