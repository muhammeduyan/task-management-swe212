# Task Management REST API - SWE 212

## Overview
This project is a robust, enterprise-grade RESTful API developed for the SWE 212 Project Study 2. It provides a complete Task Management system with strict adherence to layered architecture, proper data transfer mechanisms, and comprehensive exception handling. The system is designed to seamlessly manage employees, tasks, and their complex temporal assignments (Taskers).

## Technology Stack
* **Java:** Version 25
* **Framework:** Spring Boot 4.0.1
* **Build Tool:** Maven 4.0.0
* **Database:** PostgreSQL
* **Documentation:** SpringDoc OpenAPI (Swagger UI)

## Architectural Layers
The application strictly follows a layered architecture to separate concerns and prevent data leakage:
* **Controllers (`/controller`):** The entry point for the API. It exclusively handles HTTP requests and responses using DTOs. No Entity classes are ever exposed to the client.
* **Services (`/service`):** The core business logic layer. All complex tasks, Entity-to-DTO mapping, and mandatory standard output logging for data manipulations are executed here.
* **Repositories (`/repository`):** Spring Data JPA interfaces responsible for abstracting PostgreSQL database interactions.
* **Entities (`/entity`):** The core domain models mapped to the database schema.
* **Exceptions (`/exception`):** A global `@ControllerAdvice` mechanism that catches system errors and returns standardized, secure HTTP responses (e.g., 400, 404, 500) instead of exposing raw stack traces.

## Database Schema
The relational schema consists of three primary tables:
* **Employees:** Stores employee details (`id`, `name`, `department`).
* **Tasks:** Stores task definitions (`id`, `name`, `description`).
* **Taskers (Join Entity):** A complex relationship table assigning tasks to employees, uniquely tracking the exact `Task date` and `Task time` for each assignment.
