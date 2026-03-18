package com.example.taskmanagement.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TaskerResponseDTO {
    private Integer id;
    private EmployeeResponseDTO employee;
    private TaskResponseDTO task;
    private LocalDate taskDate;
    private LocalTime taskTime;
}
