package com.example.taskmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TaskerResponseDTO(
        Integer id,
        Integer employeeId,
        Integer taskId,
        LocalDate taskDate,
        LocalTime taskTime
) {
}
