package com.loanapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loanapp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByLoanId(Long userId);
	
	List<Payment> findByUserId(Long userId);

}
