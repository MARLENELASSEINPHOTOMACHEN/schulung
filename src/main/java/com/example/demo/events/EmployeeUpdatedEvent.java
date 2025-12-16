package com.example.demo.events;

import java.math.BigDecimal;
import java.util.UUID;

public record EmployeeUpdatedEvent(UUID employeeId, BigDecimal hourlyWage) {
}
