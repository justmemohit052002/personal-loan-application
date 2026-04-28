package com.loanapp.dto;

import com.loanapp.enums.LoanStatus;

public class LoanResponse {

    private Long id;
    private Double amount;
    private Integer tenure;
    private LoanStatus status;

    public LoanResponse(Long id, Double amount, Integer tenure, LoanStatus status) {
        this.id = id;
        this.amount = amount;
        this.tenure = tenure;
        this.status = status;
    }

    public Long getId() { return id; }
    public Double getAmount() { return amount; }
    public Integer getTenure() { return tenure; }
    public LoanStatus getStatus() { return status; }
}