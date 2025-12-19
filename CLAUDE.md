# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Zeiterfassung** (time tracking) Spring Boot 4.0.0 application (Java 25) using Maven. It provides REST APIs for employee clock-in/clock-out functionality and pay rate management, integrated with Keycloak for OAuth2 authentication and RabbitMQ for event-driven employee synchronization.

## Build and Run Commands

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Run with local profile (uses local RabbitMQ defaults)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=SchulungApplicationTests

# Run a specific test method
./mvnw test -Dtest=SchulungApplicationTests#contextLoads

# Docker: build and run with RabbitMQ
docker compose up --build
```

## Environment Variables

**Required (production):**
- `RABBITMQ_HOST`, `RABBITMQ_USERNAME`, `RABBITMQ_PASSWORD` - RabbitMQ connection
- `KEYCLOAK_ISSUER_URI` - Keycloak JWT issuer URI for OAuth2 validation

**Optional:**
- `RABBITMQ_PORT` - RabbitMQ port (default: 5672)
- `DATABASE_PATH` - SQLite database file path (default: `zeiterfassung.db` in working directory)

## Architecture

### Layered Structure
- **Controllers** (`controller/`) - REST endpoints with role-based access via `@PreAuthorize`
- **Services** (`service/`) - Business logic layer
- **Repositories** (`model/repository/`) - Spring Data JPA interfaces
- **DAOs** (`model/dao/`) - JPA entities with Lombok annotations

### Security Model
Uses Spring Security OAuth2 Resource Server with Keycloak JWT validation. Method-level security via `@PreAuthorize`:
- Employees can access their own data (matched by `authentication.name` == employeeId)
- MANAGER role can view all employee data
- ADMIN role has full access including delete and pay rate management

**Security toggle (`app.security.enabled`):**
- `SecurityConfig` provides two conditional beans based on `app.security.enabled` property
- When `true` (default): Requires JWT authentication for all requests
- When `false`: Permits all requests without authentication (for local development)
- `MethodSecurityConfig` enables `@PreAuthorize` annotations only when security is enabled

### Event-Driven Integration
RabbitMQ listeners (`events/`) subscribe to employee lifecycle events from an external HR system:
- `employee.created` - Logs new employee
- `employee.updated` - Syncs pay rate from HR system
- `employee.deleted` - Soft deletes associated time entries and pay rates
- Exchange: `employee.events` (topic exchange)

### Persistence
SQLite database (`zeiterfassung.db`) with Hibernate auto-schema updates.

## API Endpoints

| Endpoint | Method | Description | Required Role |
|----------|--------|-------------|---------------|
| `/api/v1/time-entries/clock-in/{employeeId}` | POST | Clock in | Owner/MANAGER/ADMIN |
| `/api/v1/time-entries/clock-out/{employeeId}` | POST | Clock out | Owner/MANAGER/ADMIN |
| `/api/v1/time-entries/current/{employeeId}` | GET | Current active entry | Owner/MANAGER/ADMIN |
| `/api/v1/time-entries/employee/{employeeId}` | GET | Employee history | Owner/MANAGER/ADMIN |
| `/api/v1/time-entries` | GET | All entries | MANAGER/ADMIN |
| `/api/v1/time-entries/{id}` | DELETE | Delete entry | ADMIN |
| `/api/v1/pay-rates/{employeeId}` | POST | Set pay rate | ADMIN |
| `/api/v1/pay-rates/{employeeId}/current` | GET | Current pay rate | Owner/MANAGER/ADMIN |
| `/api/v1/pay-rates/{employeeId}/history` | GET | Pay rate history | Owner/MANAGER/ADMIN |
