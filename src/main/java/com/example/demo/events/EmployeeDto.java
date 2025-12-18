package com.example.demo.events;

import java.util.UUID;

public record EmployeeDto(
    UUID id,
    String name,
    String surname,
    Long hourlyWage,
    Role role
) {
}
