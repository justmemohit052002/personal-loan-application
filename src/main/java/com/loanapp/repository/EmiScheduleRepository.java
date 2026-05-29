package com.loanapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loanapp.entity.EmiSchedule;
import com.loanapp.enums.EmiStatus;

public interface EmiScheduleRepository
        extends JpaRepository<EmiSchedule, Long> {

    List<EmiSchedule> findByLoanId(Long loanId);

    List<EmiSchedule> findByStatus(EmiStatus status);

    EmiSchedule findByLoanIdAndEmiNumber(
            Long loanId,
            Integer emiNumber
    );


}