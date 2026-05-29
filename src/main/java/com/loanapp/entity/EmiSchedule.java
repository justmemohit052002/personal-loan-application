package com.loanapp.entity;

import java.time.LocalDate;

import com.loanapp.enums.EmiStatus;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "emi_schedule")
public class EmiSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer emiNumber;

    private Double emiAmount;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private EmiStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @PrePersist
    public void onCreate() {
        status = EmiStatus.PENDING;
    }

    // getters setters
}