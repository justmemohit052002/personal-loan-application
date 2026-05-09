package com.loanapp.entity;

import com.loanapp.enums.CreditDecision;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_scores")
public class CreditScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private Integer creditScore;

	private Integer totalLoans;

	private Integer approvedLoans;

	private Integer rejectedLoans;

	private Integer activeLoans;

	private Double monthlyIncome;

	private Boolean allDocumentsVerified;

	@Enumerated(EnumType.STRING)
	private CreditDecision decision;

	private String remarks;

}
