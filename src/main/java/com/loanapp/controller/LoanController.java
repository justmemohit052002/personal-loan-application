package com.loanapp.controller;

import com.loanapp.dto.LoanRequest;
import com.loanapp.dto.LoanResponse;
import com.loanapp.entity.Loan;
import com.loanapp.security.CustomUserDetails;
import com.loanapp.service.LoanService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // ============================
    // ✅ USER APIs
    // ============================

    @PostMapping("/apply")
    public ResponseEntity<?> applyLoan(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody LoanRequest request) {

        Long userId = userDetails.getUser().getId();

        Loan loan = loanService.applyLoan(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new LoanResponse(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTenure(),
                        loan.getStatus()
                )
        );
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyLoans(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();

        List<LoanResponse> loans = loanService.getUserLoans(userId)
                .stream()
                .map(l -> new LoanResponse(
                        l.getId(),
                        l.getAmount(),
                        l.getTenure(),
                        l.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(loans);
    }

    // ============================
    // 🥉 ADMIN API (PUT BEFORE /{id})
    // ============================

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllLoans() {

        List<LoanResponse> loans = loanService.getAllLoans()
                .stream()
                .map(l -> new LoanResponse(
                        l.getId(),
                        l.getAmount(),
                        l.getTenure(),
                        l.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(loans);
    }

    // ============================
    // 🔐 GET BY ID (FIXED)
    // ============================

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getLoan(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();

        Loan loan = loanService.getLoanById(id, userId);

        return ResponseEntity.ok(
                new LoanResponse(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTenure(),
                        loan.getStatus()
                )
        );
    }

    // ============================
    // 🥈 LOAN OFFICER APIs
    // ============================

    @GetMapping("/pending")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> getPendingLoans() {

        List<LoanResponse> loans = loanService.getPendingLoans()
                .stream()
                .map(l -> new LoanResponse(
                        l.getId(),
                        l.getAmount(),
                        l.getTenure(),
                        l.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(loans);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> approveLoan(@PathVariable Long id) {

        Loan loan = loanService.approveLoan(id);

        return ResponseEntity.ok(
                new LoanResponse(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTenure(),
                        loan.getStatus()
                )
        );
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> rejectLoan(@PathVariable Long id) {

        Loan loan = loanService.rejectLoan(id);

        return ResponseEntity.ok(
                new LoanResponse(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTenure(),
                        loan.getStatus()
                )
        );
    }

    // ============================
    // 🥉 ADMIN OVERRIDE
    // ============================

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLoanStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Loan loan = loanService.updateLoanStatus(id, status);

        return ResponseEntity.ok(
                new LoanResponse(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTenure(),
                        loan.getStatus()
                )
        );
        
    }
    
    @PutMapping("/{loanId}/final-decision")
    @PreAuthorize("hasRole('LOAN_OFFICER')")
    public ResponseEntity<?> finalDecision(@PathVariable Long loanId) {

        Loan loan = loanService.finalDecision(loanId);

        return ResponseEntity.ok(
                new LoanResponse(
                        loan.getId(),
                        loan.getAmount(),
                        loan.getTenure(),
                        loan.getStatus()
                )
        );
    }
}