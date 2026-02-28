package com.example.taskmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TaskerResponseDTO(
        Integer id,
        EmployeeResponseDTO employee,
        TaskResponseDTO task,
        LocalDate taskDate,
        LocalTime taskTime
) {
}
