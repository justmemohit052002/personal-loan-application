package com.loanapp.service;

import com.loanapp.dto.LoanRequest;
import com.loanapp.entity.Loan;
import com.loanapp.entity.User;
import com.loanapp.enums.LoanStatus;
import com.loanapp.exception.UserNotFoundException;
import com.loanapp.repository.LoanRepository;
import com.loanapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CreditScoreService creditScoreService;

    // ============================
    // ✅ TASK 1: USER FLOW
    // ============================

    
    @Override
    public Loan applyLoan(Long userId, LoanRequest request) {

        User user = userRepository.findActiveUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Loan loan = new Loan();

        loan.setAmount(request.getAmount());
        loan.setTenure(request.getTenure());
        loan.setMonthlyIncome(request.getMonthlyIncome());
        loan.setPurpose(request.getPurpose());

        loan.setInterestRate(10.0);
        loan.setUser(user);

        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getUserLoans(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public Loan getLoanById(Long id, Long userId) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // 🔐 SECURITY CHECK
        if (!loan.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return loan;
    }

    // ============================
    // 🥈 TASK 2: LOAN OFFICER FLOW
    // ============================

    @Override
    public List<Loan> getPendingLoans() {
        return loanRepository.findByStatus(LoanStatus.PENDING);
    }

    @Override
    public Loan approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setStatus(LoanStatus.APPROVED);

        return loanRepository.save(loan);
    }

    @Override
    public Loan rejectLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setStatus(LoanStatus.REJECTED);

        return loanRepository.save(loan);
    }

    // ============================
    // 🥉 TASK 3: ADMIN FLOW
    // ============================

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan updateLoanStatus(Long id, String status) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        try {
            LoanStatus newStatus = LoanStatus.valueOf(status.toUpperCase());
            loan.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }

        return loanRepository.save(loan);
    }
    
    // ============================
    // 🥉 TASK 4: LOAN OFFICER - AUTOMATED DECISION
    // ============================

    @Override
    public Loan finalDecision(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        creditScoreService.calculateCreditScore(loanId);

        loan = loanRepository.findById(loanId).get();

        if (Boolean.TRUE.equals(loan.getEligible())) {
            loan.setStatus(LoanStatus.APPROVED);
        } else {
            loan.setStatus(LoanStatus.REJECTED);
        }

        return loanRepository.save(loan);
    }
}