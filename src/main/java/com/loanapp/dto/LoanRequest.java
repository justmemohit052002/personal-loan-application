package com.loanapp.dto;

import jakarta.validation.constraints.*;

public class LoanRequest {

    @NotNull
    private Double amount;

    @NotNull
    private Integer tenure;

    @NotNull
    private Double monthlyIncome;

    private String purpose;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public Double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

    
}