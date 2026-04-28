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

    // ✅ APPLY LOAN
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

    // ✅ GET USER LOANS
    @Override
    public List<Loan> getUserLoans(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    // 🔐 GET LOAN BY ID (SECURE)
    @Override
    public Loan getLoanById(Long id, Long userId) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return loan;
    }

    // ============================
    // 🥈 TASK 2: LOAN OFFICER FLOW
    // ============================

    // 🔥 GET PENDING LOANS
    @Override
    public List<Loan> getPendingLoans() {
        return loanRepository.findByStatus(LoanStatus.PENDING);
    }

    // 🔥 APPROVE LOAN
    @Override
    public Loan approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // ⚠️ prevent re-processing
        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setStatus(LoanStatus.APPROVED);

        return loanRepository.save(loan);
    }

    // 🔥 REJECT LOAN
    @Override
    public Loan rejectLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // ⚠️ prevent re-processing
        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setStatus(LoanStatus.REJECTED);

        return loanRepository.save(loan);
    }
}