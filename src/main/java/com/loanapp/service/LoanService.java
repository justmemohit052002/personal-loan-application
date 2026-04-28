package com.loanapp.service;

import com.loanapp.dto.LoanRequest;
import com.loanapp.entity.Loan;

import java.util.List;

public interface LoanService {

    // TASK 1
    Loan applyLoan(Long userId, LoanRequest request);

    List<Loan> getUserLoans(Long userId);

    Loan getLoanById(Long id, Long userId);

    // TASK 2 (ADD THESE)
    List<Loan> getPendingLoans();

    Loan approveLoan(Long loanId);

    Loan rejectLoan(Long loanId);
}