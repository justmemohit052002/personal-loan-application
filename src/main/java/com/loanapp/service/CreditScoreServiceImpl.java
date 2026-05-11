package com.loanapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanapp.dto.CreditScoreResponseDto;
import com.loanapp.entity.Document;
import com.loanapp.entity.Loan;
import com.loanapp.enums.DocumentStatus;
import com.loanapp.enums.LoanStatus;
import com.loanapp.repository.DocumentRepository;
import com.loanapp.repository.LoanRepository;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public CreditScoreResponseDto calculateCreditScore(Long loanId) {

        // =========================
        // FETCH LOAN
        // =========================

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));

        Long userId = loan.getUser().getId();

        // =========================
        // FETCH PREVIOUS LOANS
        // =========================

        List<Loan> previousLoans = loanRepository
                .findByUserId(userId)
                .stream()
                .filter(l -> !l.getId().equals(loanId))
                .toList();

        // =========================
        // FETCH DOCUMENTS
        // =========================

        List<Document> documents =
                documentRepository.findByLoanId(loanId);

        // =========================
        // BASE SCORE
        // =========================

        int score = 300;

        // =========================
        // CREDIT HISTORY
        // =========================

        if (previousLoans.isEmpty()) {

            // No previous bad history
            score += 100;
        }

        int approvedLoans = 0;
        int rejectedLoans = 0;
        int activeLoans = 0;

        for (Loan l : previousLoans) {

            // Previous approved loans
            if (l.getStatus() == LoanStatus.APPROVED) {

                approvedLoans++;

                score += 100;
            }

            // Previous rejected loans
            if (l.getStatus() == LoanStatus.REJECTED) {

                rejectedLoans++;

                score -= 100;
            }

            // Existing active loans
            if (l.getStatus() == LoanStatus.PENDING) {

                activeLoans++;

                score -= 50;
            }
        }

        // =========================
        // DOCUMENT VERIFICATION
        // =========================

        boolean allDocsApproved =
                !documents.isEmpty()
                &&
                documents.stream()
                        .allMatch(doc ->
                                doc.getStatus()
                                        == DocumentStatus.APPROVED
                        );

        if (allDocsApproved) {

            // All documents verified
            score += 250;

            loan.setDocumentsVerified(true);

        } else {

            loan.setDocumentsVerified(false);
        }

        // =========================
        // MONTHLY INCOME ANALYSIS
        // =========================

        if (loan.getMonthlyIncome() >= 100000) {

            // Excellent income
            score += 200;

        } else if (loan.getMonthlyIncome() >= 50000) {

            // Good income
            score += 100;

        } else if (loan.getMonthlyIncome() >= 30000) {

            // Average income
            score += 50;

        } else {

            // Weak income
            score -= 100;
        }

        // =========================
        // LOAN AMOUNT RISK
        // =========================

        if (loan.getAmount() > 1000000) {

            // Very high loan amount
            score -= 150;

        } else if (loan.getAmount() > 500000) {

            // Moderately high loan amount
            score -= 50;
        }

        // =========================
        // AFFORDABILITY CHECK
        // =========================

        double yearlyIncome =
                loan.getMonthlyIncome() * 12;

        double loanRatio =
                loan.getAmount() / yearlyIncome;

        if (loanRatio <= 2) {

            // Easily affordable
            score += 100;

        } else if (loanRatio <= 4) {

            // Moderately affordable
            score += 50;

        } else {

            // Risky loan burden
            score -= 100;
        }

        // =========================
        // NORMALIZE SCORE
        // =========================

        if (score > 900) {
            score = 900;
        }

        if (score < 300) {
            score = 300;
        }

        // =========================
        // ELIGIBILITY
        // =========================

        boolean eligible =
                score >= 650
                &&
                allDocsApproved;

        // =========================
        // SAVE LOAN DATA
        // =========================

        loan.setCreditScore(score);

        loan.setEligible(eligible);

        loanRepository.save(loan);

        // =========================
        // RISK LEVEL
        // =========================

        String risk;

        if (score >= 750) {

            risk = "LOW";

        } else if (score >= 650) {

            risk = "MEDIUM";

        } else {

            risk = "HIGH";
        }

        // =========================
        // RESPONSE
        // =========================

        return new CreditScoreResponseDto(
                score,
                eligible,
                risk,
                eligible
                        ? "Customer eligible for loan"
                        : "Customer not eligible for loan"
        );
    }
}