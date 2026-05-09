package com.loanapp.service;
import com.loanapp.dto.CreditScoreResponseDto;
public interface CreditScoreService {
	
	CreditScoreResponseDto calculateCreditScore(Long loanId);

}
