package com.loanapp.entity;

import com.loanapp.enums.LoanStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private Integer tenure; // months

    private Double interestRate;

    private Double monthlyIncome;

    private String purpose;
    
    private Integer creditScore;
    
    private Boolean eligible = false;
    
    private Boolean documentsVerified = false;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    // 🔗 Relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    public Loan() {}

    // 🔥 Automatically set default values
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = LoanStatus.PENDING;
    }

    // ========================
    // GETTERS & SETTERS
    // ========================

    public Long getId() {
        return id;
    }

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

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
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

    // 🔥 IMPORTANT (FIXED)
    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public Boolean getDocumentsVerified() {
        return documentsVerified;
    }

    public void setDocumentsVerified(Boolean documentsVerified) {
        this.documentsVerified = documentsVerified;
    }
}