package com.loanapp.service;

import java.util.List;
import java.util.Optional;

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

	        Loan loan = loanRepository.findById(loanId)
	                .orElseThrow(() -> new RuntimeException("Loan not found"));

	        Long userId = loan.getUser().getId();

	        List<Loan> previousLoans = loanRepository.findByUserId(userId);
	        
	        //Changed from list to optional and findbyLoanId to findById 
	        Optional<Document> documents = documentRepository.findById(loanId);
	        int score = 300;

	        // =========================
	        // 1. CREDIT HISTORY CHECK
	        // =========================

	        if (previousLoans.isEmpty()) {
	            score += 100;
	        }

	        for (Loan l : previousLoans) {

	            if (l.getStatus() == LoanStatus.APPROVED) {
	                score += 150;
	            }

	            if (l.getStatus() == LoanStatus.REJECTED) {
	                score -= 100;
	            }
	        }
	        
	     // =========================
	        // 2. DOCUMENT VERIFICATION
	        // =========================

	        boolean allDocsApproved = documents.stream()
	                .allMatch(doc -> doc.getStatus() == DocumentStatus.APPROVED);

	        if (allDocsApproved && !documents.isEmpty()) {
	            score += 250;
	            loan.setDocumentsVerified(true);
	        }

	        // =========================
	        // 3. LOAN AMOUNT RISK
	        // =========================

	        if (loan.getAmount() > 500000) {
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
	        // ELIGIBILITY CHECK
	        // =========================

	        boolean eligible = score >= 700 && allDocsApproved;

	        loan.setCreditScore(score);
	        loan.setEligible(eligible);

	        loanRepository.save(loan);

	        String risk;

	        if (score >= 750) {
	            risk = "LOW";
	        } else if (score >= 600) {
	            risk = "MEDIUM";
	        } else {
	            risk = "HIGH";
	        }

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