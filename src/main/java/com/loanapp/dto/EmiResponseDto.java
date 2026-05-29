package com.loanapp.dto;

import java.time.LocalDate;

import com.loanapp.enums.EmiStatus;
import lombok.Data;


@Data
public class EmiResponseDto {

    private Integer emiNumber;

    private Double emiAmount;

    private LocalDate dueDate;

    private EmiStatus status;

    // getters setters
}