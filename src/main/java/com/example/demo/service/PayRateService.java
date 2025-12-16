package com.example.demo.service;

import com.example.demo.model.dao.PayRate;
import com.example.demo.model.repository.PayRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayRateService {

    private final PayRateRepository payRateRepository;

    public PayRate setPayRate(UUID employeeId, BigDecimal hourlyWage, LocalDate validFrom) {
        return payRateRepository.save(new PayRate(employeeId, hourlyWage, validFrom));
    }

    public Optional<PayRate> getCurrentPayRate(UUID employeeId) {
        return payRateRepository.findFirstByEmployeeIdAndValidFromLessThanEqualAndDeletedAtIsNullOrderByValidFromDesc(
                employeeId, LocalDate.now());
    }

    public List<PayRate> getPayRateHistory(UUID employeeId) {
        return payRateRepository.findByEmployeeIdAndDeletedAtIsNullOrderByValidFromDesc(employeeId);
    }

    public void softDeleteByEmployeeId(UUID employeeId) {
        List<PayRate> payRates = payRateRepository.findByEmployeeId(employeeId);
        LocalDateTime now = LocalDateTime.now();
        payRates.forEach(payRate -> payRate.setDeletedAt(now));
        payRateRepository.saveAll(payRates);
    }
}
