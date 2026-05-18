package com.loanapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentRequestDto {
	private Long loanId;

	private Double amount;

	private Integer emiMonth;

}
