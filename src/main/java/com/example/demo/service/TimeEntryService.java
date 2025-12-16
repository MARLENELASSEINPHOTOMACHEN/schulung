package com.example.demo.service;

import com.example.demo.model.dao.TimeEntry;
import com.example.demo.model.repository.TimeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;

    public TimeEntry clockIn(UUID employeeId) {
        if (getCurrentEntry(employeeId).isPresent()) {
            throw new IllegalStateException("Already clocked in");
        }
        return timeEntryRepository.save(new TimeEntry(employeeId));
    }

    public TimeEntry clockOut(UUID employeeId) {
        TimeEntry entry = getCurrentEntry(employeeId)
                .orElseThrow(() -> new IllegalStateException("Not clocked in"));
        entry.setClockOut(LocalDateTime.now());
        return timeEntryRepository.save(entry);
    }

    public Optional<TimeEntry> getCurrentEntry(UUID employeeId) {
        return timeEntryRepository.findByEmployeeIdAndClockOutIsNull(employeeId);
    }

    public List<TimeEntry> getEntriesForEmployee(UUID employeeId) {
        return timeEntryRepository.findByEmployeeIdOrderByClockInDesc(employeeId);
    }

    public List<TimeEntry> getAllEntries() {
        return timeEntryRepository.findAll();
    }

    public Optional<TimeEntry> getEntry(UUID id) {
        return timeEntryRepository.findById(id);
    }

    public void deleteEntry(UUID id) {
        timeEntryRepository.deleteById(id);
    }
}
