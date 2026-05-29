package com.loanapp.service;

import java.util.List;

import com.loanapp.dto.EmiResponseDto;
import com.loanapp.entity.Loan;

public interface EmiScheduleService {

    void generateSchedule(Loan loan);

    List<EmiResponseDto> getSchedule(Long loanId);
}