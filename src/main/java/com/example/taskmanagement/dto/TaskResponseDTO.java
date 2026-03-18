package com.example.taskmanagement.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TaskResponseDTO {
    private Integer id;
    private String name;
    private String description;
}
