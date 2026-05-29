package com.loanapp.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.loanapp.entity.EmiSchedule;
import com.loanapp.enums.EmiStatus;
import com.loanapp.repository.EmiScheduleRepository;

@Component
public class EmiReminderScheduler {

    @Autowired
    private EmiScheduleRepository emiRepository;

    @Scheduled(cron = "0 0 9 * * ?")
    public void markOverdueEmis() {

        List<EmiSchedule> emis =
                emiRepository.findByStatus(
                        EmiStatus.PENDING
                );

        for (EmiSchedule emi : emis) {

            if (emi.getDueDate()
                    .isBefore(LocalDate.now())) {

                emi.setStatus(
                        EmiStatus.OVERDUE
                );

                emiRepository.save(emi);
            }
        }
    }
}