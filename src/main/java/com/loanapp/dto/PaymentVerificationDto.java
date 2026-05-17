package com.loanapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentVerificationDto {
	private String razorpayOrderId;

    private String razorpayPaymentId;

    private Long paymentId;


}
