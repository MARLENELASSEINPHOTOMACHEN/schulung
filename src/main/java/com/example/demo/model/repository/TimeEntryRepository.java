package com.example.demo.model.repository;

import com.example.demo.model.dao.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, UUID> {

    List<TimeEntry> findByEmployeeIdAndDeletedAtIsNull(UUID employeeId);

    Optional<TimeEntry> findByEmployeeIdAndClockOutIsNullAndDeletedAtIsNull(UUID employeeId);

    List<TimeEntry> findByEmployeeIdAndDeletedAtIsNullOrderByClockInDesc(UUID employeeId);

    List<TimeEntry> findAllByDeletedAtIsNull();

    List<TimeEntry> findByEmployeeId(UUID employeeId);
}
