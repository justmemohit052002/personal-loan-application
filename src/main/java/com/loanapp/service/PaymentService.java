package com.loanapp.service;

import com.loanapp.dto.CreatePaymentRequestDto;
import com.loanapp.dto.PaymentResponseDto;
import com.loanapp.dto.PaymentVerificationDto;

public interface PaymentService {
	PaymentResponseDto createOrder(Long userId, CreatePaymentRequestDto dto) throws Exception;

	String verifyPayment(PaymentVerificationDto dto);

}
