package com.example.demo.controller;

import com.example.demo.events.EmployeeDto;
import com.example.demo.events.Role;
import com.example.demo.model.dao.PayRate;
import com.example.demo.model.dao.TimeEntry;
import com.example.demo.model.repository.PayRateRepository;
import com.example.demo.model.repository.TimeEntryRepository;
import com.example.demo.service.PayRateService;
import com.example.demo.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestEventController {

    private final TimeEntryService timeEntryService;
    private final PayRateService payRateService;
    private final TimeEntryRepository timeEntryRepository;
    private final PayRateRepository payRateRepository;

    @PostMapping("/events/employee-created")
    public ResponseEntity<String> simulateEmployeeCreated(@RequestBody EmployeeDto employee) {
        log.info("Simulating employee.created event for: {} {} ({})",
                employee.name(), employee.surname(), employee.id());
        if (employee.hourlyWage() != null) {
            payRateService.setPayRate(employee.id(), BigDecimal.valueOf(employee.hourlyWage()), LocalDate.now());
        }
        return ResponseEntity.ok("Simulated employee.created event for " + employee.id());
    }

    @PostMapping("/events/employee-updated")
    public ResponseEntity<String> simulateEmployeeUpdated(@RequestBody EmployeeDto employee) {
        log.info("Simulating employee.updated event for: {} {} ({}) with wage: {}",
                employee.name(), employee.surname(), employee.id(), employee.hourlyWage());
        if (employee.hourlyWage() != null) {
            payRateService.setPayRate(employee.id(), BigDecimal.valueOf(employee.hourlyWage()), LocalDate.now());
        }
        return ResponseEntity.ok("Simulated employee.updated event for " + employee.id());
    }

    @PostMapping("/events/employee-deleted/{employeeId}")
    public ResponseEntity<String> simulateEmployeeDeleted(@PathVariable UUID employeeId) {
        log.info("Simulating employee.deleted event for: {}", employeeId);
        timeEntryService.softDeleteByEmployeeId(employeeId);
        payRateService.softDeleteByEmployeeId(employeeId);
        return ResponseEntity.ok("Simulated employee.deleted event for " + employeeId);
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
