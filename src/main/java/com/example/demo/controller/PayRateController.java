package com.example.demo.controller;

import com.example.demo.model.dao.PayRate;
import com.example.demo.service.PayRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pay-rates")
@RequiredArgsConstructor
public class PayRateController {

    private final PayRateService payRateService;

    @PostMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PayRate> setPayRate(
            @PathVariable UUID employeeId,
            @RequestParam BigDecimal hourlyWage,
            @RequestParam(required = false) LocalDate validFrom) {
        LocalDate date = validFrom != null ? validFrom : LocalDate.now();
        return ResponseEntity.ok(payRateService.setPayRate(employeeId, hourlyWage, date));
    }

    @GetMapping("/{employeeId}/current")
    @PreAuthorize("#employeeId.toString() == authentication.name or hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<PayRate> getCurrentPayRate(@PathVariable UUID employeeId) {
        return payRateService.getCurrentPayRate(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{employeeId}/history")
    @PreAuthorize("#employeeId.toString() == authentication.name or hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<PayRate>> getPayRateHistory(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(payRateService.getPayRateHistory(employeeId));
    }
}
