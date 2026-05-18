package com.loanapp.service;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.loanapp.dto.CreatePaymentRequestDto;
import com.loanapp.dto.PaymentResponseDto;
import com.loanapp.dto.PaymentVerificationDto;
import com.loanapp.entity.Loan;
import com.loanapp.entity.Payment;
import com.loanapp.entity.User;
import com.loanapp.enums.PaymentStatus;
import com.loanapp.repository.LoanRepository;
import com.loanapp.repository.PaymentRepository;
import com.loanapp.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Value("${razorpay.key-id}")
	private String razorpayKeyId;

	@Value("${razorpay.key-secret}")
	private String razorpaySecret;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public PaymentResponseDto createOrder(Long userId, CreatePaymentRequestDto dto) throws Exception {

		Loan loan = loanRepository.findById(dto.getLoanId()).orElseThrow(() -> new RuntimeException("Loan not found"));

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecret);

		JSONObject orderRequest = new JSONObject();

		orderRequest.put("amount", dto.getAmount() * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

		Order order = razorpay.orders.create(orderRequest);

		Payment payment = new Payment();

		payment.setAmount(dto.getAmount());
		payment.setLoan(loan);
		payment.setUser(user);
		payment.setEmiMonth(dto.getEmiMonth());
		payment.setStatus(PaymentStatus.CREATED);
		payment.setRazorpayOrderId(order.get("id"));

		paymentRepository.save(payment);

		return new PaymentResponseDto(payment.getId(), payment.getRazorpayOrderId(), payment.getAmount(),
				payment.getStatus().name());
	}

	@Override
	public String verifyPayment(PaymentVerificationDto dto) {

		Payment payment = paymentRepository.findById(dto.getPaymentId())
				.orElseThrow(() -> new RuntimeException("Payment not found"));

		payment.setRazorpayPaymentId(dto.getRazorpayPaymentId());
		payment.setStatus(PaymentStatus.SUCCESS);
		payment.setPaymentDate(LocalDateTime.now());

		paymentRepository.save(payment);

		return "EMI payment successful";
	}
}
