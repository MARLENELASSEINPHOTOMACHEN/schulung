package com.example.demo.events;

import com.example.demo.service.PayRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeEventListener {

    private final PayRateService payRateService;

    @RabbitListener(queues = RabbitMQConfig.EMPLOYEE_CREATED_QUEUE)
    public void handleEmployeeCreated(UUID employeeId) {
        log.info("Employee created: {}", employeeId);
    }

    @RabbitListener(queues = RabbitMQConfig.EMPLOYEE_UPDATED_QUEUE)
    public void handleEmployeeUpdated(EmployeeUpdatedEvent event) {
        log.info("Employee updated: {} with hourly wage {}", event.employeeId(), event.hourlyWage());
        payRateService.setPayRate(event.employeeId(), event.hourlyWage(), LocalDate.now());
    }

    @RabbitListener(queues = RabbitMQConfig.EMPLOYEE_DELETED_QUEUE)
    public void handleEmployeeDeleted(UUID employeeId) {
        log.info("Employee deleted: {}", employeeId);
    }
}
