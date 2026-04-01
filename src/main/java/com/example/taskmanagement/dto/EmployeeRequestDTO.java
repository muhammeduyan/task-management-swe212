package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 16, message = "Name must be at most 16 characters")
    private String name;

    @NotBlank(message = "Department cannot be empty")
    @Size(max = 16, message = "Department must be at most 16 characters")
    private String department;
}
