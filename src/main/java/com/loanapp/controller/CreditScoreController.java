package com.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanapp.dto.CreditScoreResponseDto;
import com.loanapp.service.CreditScoreService;

@RestController
@RequestMapping("/api/credit-score")
public class CreditScoreController {

	@Autowired
	private CreditScoreService creditScoreService;

	@GetMapping("/{loanId}")
	@PreAuthorize("hasRole('LOAN_OFFICER')")
	public ResponseEntity<CreditScoreResponseDto> getCreditScore(@PathVariable Long loanId) {

		return ResponseEntity.ok(creditScoreService.calculateCreditScore(loanId));
	}
}
