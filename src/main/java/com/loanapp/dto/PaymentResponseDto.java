package com.loanapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDto {
	private Long paymentId;

	private String razorpayOrderId;

	private Double amount;

	private String status;

}
