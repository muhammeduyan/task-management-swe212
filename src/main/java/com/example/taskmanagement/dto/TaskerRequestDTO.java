package com.example.taskmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TaskerRequestDTO(
        Integer employeeId,
        Integer taskId,
        LocalDate taskDate,
        LocalTime taskTime
) {
}
