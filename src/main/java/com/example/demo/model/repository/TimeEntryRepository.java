package com.example.demo.model.repository;

import com.example.demo.model.dao.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, UUID> {

    List<TimeEntry> findByEmployeeId(UUID employeeId);

    Optional<TimeEntry> findByEmployeeIdAndClockOutIsNull(UUID employeeId);

    List<TimeEntry> findByEmployeeIdOrderByClockInDesc(UUID employeeId);
}
