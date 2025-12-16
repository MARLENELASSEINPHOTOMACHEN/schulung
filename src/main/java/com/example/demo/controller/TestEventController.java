package com.example.demo.controller;

import com.example.demo.model.dao.PayRate;
import com.example.demo.model.dao.TimeEntry;
import com.example.demo.model.repository.PayRateRepository;
import com.example.demo.model.repository.TimeEntryRepository;
import com.example.demo.service.PayRateService;
import com.example.demo.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@Profile("local")
@RequiredArgsConstructor
@Slf4j
public class TestEventController {

    private final TimeEntryService timeEntryService;
    private final PayRateService payRateService;
    private final TimeEntryRepository timeEntryRepository;
    private final PayRateRepository payRateRepository;

    @PostMapping("/events/employee-deleted/{employeeId}")
    public ResponseEntity<String> simulateEmployeeDeleted(@PathVariable UUID employeeId) {
        log.info("Simulating employee.deleted event for: {}", employeeId);
        timeEntryService.softDeleteByEmployeeId(employeeId);
        payRateService.softDeleteByEmployeeId(employeeId);
        return ResponseEntity.ok("Simulated employee.deleted event for " + employeeId);
    }

    @PostMapping("/events/employee-updated/{employeeId}")
    public ResponseEntity<String> simulateEmployeeUpdated(
            @PathVariable UUID employeeId,
            @RequestParam BigDecimal hourlyWage) {
        log.info("Simulating employee.updated event for: {} with wage: {}", employeeId, hourlyWage);
        payRateService.setPayRate(employeeId, hourlyWage, LocalDate.now());
        return ResponseEntity.ok("Simulated employee.updated event for " + employeeId);
    }

    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> getDatabaseContents() {
        List<TimeEntry> timeEntries = timeEntryRepository.findAll();
        List<PayRate> payRates = payRateRepository.findAll();
        return ResponseEntity.ok(Map.of(
                "timeEntries", timeEntries,
                "payRates", payRates
        ));
    }
}
