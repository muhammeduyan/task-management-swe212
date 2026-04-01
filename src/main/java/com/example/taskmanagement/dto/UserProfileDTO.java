package com.example.taskmanagement.dto;

import lombok.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String profileImage;
    private Set<String> roles;
}
