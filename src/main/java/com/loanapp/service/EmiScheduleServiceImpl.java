package com.loanapp.service;

import java.time.LocalDate;
import java.util.List;

import com.loanapp.dto.EmiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanapp.entity.EmiSchedule;
import com.loanapp.entity.Loan;
import com.loanapp.enums.EmiStatus;
import com.loanapp.repository.EmiScheduleRepository;

@Service
public class EmiScheduleServiceImpl
        implements EmiScheduleService {

    @Autowired
    private EmiScheduleRepository emiRepository;

    @Override
    public List<EmiResponseDto> getSchedule(Long loanId) {

        return emiRepository.findByLoanId(loanId)
                .stream()
                .map(emi -> {

                    EmiResponseDto dto =
                            new EmiResponseDto();

                    dto.setEmiNumber(
                            emi.getEmiNumber()
                    );

                    dto.setEmiAmount(
                            emi.getEmiAmount()
                    );

                    dto.setDueDate(
                            emi.getDueDate()
                    );

                    dto.setStatus(
                            emi.getStatus()
                    );

                    return dto;

                })
                .toList();
    }

    @Override
    public void generateSchedule(Loan loan) {

        double emiAmount =
                loan.getAmount() /
                        loan.getTenure();

        for(int i = 1; i <= loan.getTenure(); i++) {

            EmiSchedule emi =
                    new EmiSchedule();

            emi.setLoan(loan);

            emi.setEmiNumber(i);

            emi.setEmiAmount(emiAmount);

            emi.setDueDate(
                    LocalDate.now().plusMonths(i)
            );

            emi.setStatus(
                    EmiStatus.PENDING
            );

            emiRepository.save(emi);
        }
    }
}