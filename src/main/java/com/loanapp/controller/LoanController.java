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

    // ✅ APPLY LOAN
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

    // ✅ GET MY LOANS
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

    // 🔐 GET LOAN BY ID (SECURE)
    @GetMapping("/{id}")
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
    // 🥈 TASK 2: LOAN OFFICER APIs
    // ============================

    // 🔥 GET PENDING LOANS
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

    // 🔥 APPROVE LOAN
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

    // 🔥 REJECT LOAN
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
}