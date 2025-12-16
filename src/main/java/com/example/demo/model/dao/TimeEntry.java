package com.example.demo.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "time_entries")
@Data
@NoArgsConstructor
public class TimeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private LocalDateTime clockIn;

    private LocalDateTime clockOut;

    private LocalDateTime deletedAt;

    public TimeEntry(UUID employeeId) {
        this.employeeId = employeeId;
        this.clockIn = LocalDateTime.now();
    }

    public boolean isClockedOut() {
        return clockOut != null;
    }
}
