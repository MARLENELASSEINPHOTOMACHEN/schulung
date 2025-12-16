package com.example.demo.controller;

import com.example.demo.model.dao.TimeEntry;
import com.example.demo.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/time-entries")
@RequiredArgsConstructor
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    @PostMapping("/clock-in/{employeeId}")
    @PreAuthorize("#employeeId.toString() == authentication.name or hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TimeEntry> clockIn(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(timeEntryService.clockIn(employeeId));
    }

    @PostMapping("/clock-out/{employeeId}")
    @PreAuthorize("#employeeId.toString() == authentication.name or hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TimeEntry> clockOut(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(timeEntryService.clockOut(employeeId));
    }

    @GetMapping("/current/{employeeId}")
    @PreAuthorize("#employeeId.toString() == authentication.name or hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<TimeEntry> getCurrentEntry(@PathVariable UUID employeeId) {
        return timeEntryService.getCurrentEntry(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("#employeeId.toString() == authentication.name or hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<TimeEntry>> getEntriesForEmployee(@PathVariable UUID employeeId) {
        return ResponseEntity.ok(timeEntryService.getEntriesForEmployee(employeeId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<List<TimeEntry>> getAllEntries() {
        return ResponseEntity.ok(timeEntryService.getAllEntries());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEntry(@PathVariable UUID id) {
        timeEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}
