package com.loanapp.dto;

import com.loanapp.enums.CreditDecision;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditScoreResponseDto2 {
	private Integer creditScore;
    private Boolean eligible;
    private String riskLevel;
    private String message;
}
