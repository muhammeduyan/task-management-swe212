package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TaskerRequestDTO {
    @NotNull(message = "Employee ID cannot be null")
    private Integer employeeId;

    private Integer taskId;

    private LocalDate taskDate;

    private LocalTime taskTime;
}
