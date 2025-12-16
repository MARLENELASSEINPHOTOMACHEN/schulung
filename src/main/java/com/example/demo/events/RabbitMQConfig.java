package com.example.demo.events;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMPLOYEE_EXCHANGE = "employee.events";
    public static final String EMPLOYEE_CREATED_QUEUE = "zeiterfassung.employee.created";
    public static final String EMPLOYEE_UPDATED_QUEUE = "zeiterfassung.employee.updated";
    public static final String EMPLOYEE_DELETED_QUEUE = "zeiterfassung.employee.deleted";

    @Bean
    public TopicExchange employeeExchange() {
        return new TopicExchange(EMPLOYEE_EXCHANGE);
    }

    @Bean
    public Queue employeeCreatedQueue() {
        return new Queue(EMPLOYEE_CREATED_QUEUE, true);
    }

    @Bean
    public Queue employeeUpdatedQueue() {
        return new Queue(EMPLOYEE_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue employeeDeletedQueue() {
        return new Queue(EMPLOYEE_DELETED_QUEUE, true);
    }

    @Bean
    public Binding createdBinding(Queue employeeCreatedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeCreatedQueue).to(employeeExchange).with("employee.created");
    }

    @Bean
    public Binding updatedBinding(Queue employeeUpdatedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeUpdatedQueue).to(employeeExchange).with("employee.updated");
    }

    @Bean
    public Binding deletedBinding(Queue employeeDeletedQueue, TopicExchange employeeExchange) {
        return BindingBuilder.bind(employeeDeletedQueue).to(employeeExchange).with("employee.deleted");
    }
}
