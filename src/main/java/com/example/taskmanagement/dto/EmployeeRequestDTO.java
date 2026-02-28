package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeRequestDTO(

        @NotBlank(message = "Name cannot be empty")
        @Size(max = 16, message = "Name must be at most 16 characters")
        String name,

        @NotBlank(message = "Department cannot be empty")
        @Size(max = 16, message = "Department must be at most 16 characters")
        String department
) {
}
