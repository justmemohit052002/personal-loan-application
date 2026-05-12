// LoanServiceImpl.java

package com.loanapp.service;

import com.loanapp.dto.LoanRequest;
import com.loanapp.entity.Loan;
import com.loanapp.entity.User;
import com.loanapp.enums.LoanStatus;
import com.loanapp.exception.LoanNotFoundException;
import com.loanapp.exception.UnauthorizedAccessException;
import com.loanapp.exception.UserNotFoundException;
import com.loanapp.repository.LoanRepository;
import com.loanapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditScoreService creditScoreService;

    @Autowired
    private EmailService emailService;

    // ============================
    // APPLY LOAN
    // ============================

    @Override
    @Transactional
    public Loan applyLoan(Long userId,
                          LoanRequest request) {

        User user = userRepository.findActiveUserById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Loan loan = new Loan();

        loan.setAmount(request.getAmount());
        loan.setTenure(request.getTenure());
        loan.setMonthlyIncome(request.getMonthlyIncome());
        loan.setPurpose(request.getPurpose());

        loan.setInterestRate(10.0);
        loan.setUser(user);

        Loan savedLoan = loanRepository.save(loan);

        emailService.sendLoanSubmittedEmail(
                user.getEmail(),
                user.getFullName(),
                savedLoan.getId()
        );

        return savedLoan;
    }

    // ============================
    // GET USER LOANS
    // ============================

    @Override
    public List<Loan> getUserLoans(Long userId) {

        return loanRepository.findByUserId(userId);
    }

    // ============================
    // GET LOAN BY ID
    // ============================

    @Override
    public Loan getLoanById(Long id,
                            Long userId) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        if (!loan.getUser().getId().equals(userId)) {

            throw new UnauthorizedAccessException(
                    "Unauthorized access"
            );
        }

        return loan;
    }

    // ============================
    // GET PENDING LOANS
    // ============================

    @Override
    public List<Loan> getPendingLoans() {

        return loanRepository.findByStatus(
                LoanStatus.PENDING
        );
    }

    // ============================
    // APPROVE LOAN
    // ============================

    @Override
    @Transactional
    public Loan approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        if (!loan.getStatus().equals(
                LoanStatus.PENDING
        )) {

            throw new RuntimeException(
                    "Loan already processed"
            );
        }

        loan.setStatus(LoanStatus.APPROVED);

        Loan approvedLoan =
                loanRepository.save(loan);

        emailService.sendLoanApprovedEmail(
                loan.getUser().getEmail(),
                loan.getUser().getFullName(),
                loan.getId()
        );

        return approvedLoan;
    }

    // ============================
    // REJECT LOAN
    // ============================

    @Override
    @Transactional
    public Loan rejectLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        if (!loan.getStatus().equals(
                LoanStatus.PENDING
        )) {

            throw new RuntimeException(
                    "Loan already processed"
            );
        }

        loan.setStatus(LoanStatus.REJECTED);

        Loan rejectedLoan =
                loanRepository.save(loan);

        emailService.sendLoanRejectedEmail(
                loan.getUser().getEmail(),
                loan.getUser().getFullName(),
                loan.getId()
        );

        return rejectedLoan;
    }

    // ============================
    // GET ALL LOANS
    // ============================

    @Override
    public List<Loan> getAllLoans() {

        return loanRepository.findAll();
    }

    // ============================
    // UPDATE STATUS
    // ============================

    @Override
    @Transactional
    public Loan updateLoanStatus(Long id,
                                 String status) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        try {

            LoanStatus newStatus =
                    LoanStatus.valueOf(
                            status.toUpperCase()
                    );

            loan.setStatus(newStatus);

        } catch (IllegalArgumentException e) {

            throw new RuntimeException(
                    "Invalid status value"
            );
        }

        return loanRepository.save(loan);
    }

    // ============================
    // FINAL DECISION
    // ============================

    @Override
    @Transactional
    public Loan finalDecision(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        creditScoreService.calculateCreditScore(
                loanId
        );

        loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        if (Boolean.TRUE.equals(
                loan.getEligible()
        )) {

            loan.setStatus(LoanStatus.APPROVED);

        } else {

            loan.setStatus(LoanStatus.REJECTED);
        }

        Loan finalLoan =
                loanRepository.save(loan);

        if (loan.getStatus() ==
                LoanStatus.APPROVED) {

            emailService.sendLoanApprovedEmail(
                    loan.getUser().getEmail(),
                    loan.getUser().getFullName(),
                    loan.getId()
            );

        } else {

            emailService.sendLoanRejectedEmail(
                    loan.getUser().getEmail(),
                    loan.getUser().getFullName(),
                    loan.getId()
            );
        }

        return finalLoan;
    }
}