package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TaskRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 16, message = "Name must be at most 16 characters")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 64, message = "Description must be at most 64 characters")
    private String description;
}
