package com.example.taskmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record TaskerRequestDTO(
        @NotNull(message = "Employee ID cannot be null")
        Integer employeeId,

        Integer taskId,

        LocalDate taskDate,

        LocalTime taskTime
) {
}
