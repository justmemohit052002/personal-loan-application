package com.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loanapp.service.EmiScheduleService;

@RestController
@RequestMapping("/api/emi")
public class EmiController {

    @Autowired
    private EmiScheduleService emiScheduleService;

    @GetMapping("/{loanId}")
    public ResponseEntity<?> getSchedule(
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                emiScheduleService.getSchedule(
                        loanId
                )
        );
    }
}