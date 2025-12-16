package com.example.demo.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pay_rates")
@Data
@NoArgsConstructor
public class PayRate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private BigDecimal hourlyWage;

    @Column(nullable = false)
    private LocalDate validFrom;

    private LocalDateTime deletedAt;

    public PayRate(UUID employeeId, BigDecimal hourlyWage, LocalDate validFrom) {
        this.employeeId = employeeId;
        this.hourlyWage = hourlyWage;
        this.validFrom = validFrom;
    }
}
