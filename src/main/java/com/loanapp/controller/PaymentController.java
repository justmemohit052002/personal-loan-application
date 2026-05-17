package com.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanapp.dto.CreatePaymentRequestDto;
import com.loanapp.dto.PaymentResponseDto;
import com.loanapp.dto.PaymentVerificationDto;
import com.loanapp.security.CustomUserDetails;
import com.loanapp.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	@Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponseDto> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreatePaymentRequestDto dto) throws Exception {

        Long userId = userDetails.getUser().getId();

        return ResponseEntity.ok(
                paymentService.createOrder(userId, dto)
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @RequestBody PaymentVerificationDto dto) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(dto)
        );
    }
	

}
