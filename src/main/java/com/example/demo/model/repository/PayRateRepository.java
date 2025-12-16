package com.example.demo.model.repository;

import com.example.demo.model.dao.PayRate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayRateRepository extends JpaRepository<PayRate, UUID> {

    List<PayRate> findByEmployeeIdOrderByValidFromDesc(UUID employeeId);

    Optional<PayRate> findFirstByEmployeeIdAndValidFromLessThanEqualOrderByValidFromDesc(
            UUID employeeId, LocalDate date);
}
