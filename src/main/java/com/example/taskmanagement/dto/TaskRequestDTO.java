package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequestDTO(

        @NotBlank(message = "Name cannot be empty")
        @Size(max = 16, message = "Name must be at most 16 characters")
        String name,

        @NotBlank(message = "Description cannot be empty")
        @Size(max = 64, message = "Description must be at most 64 characters")
        String description
) {
}
