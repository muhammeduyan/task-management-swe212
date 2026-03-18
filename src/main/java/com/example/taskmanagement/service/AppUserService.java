package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.UserProfileDTO;
import com.example.taskmanagement.dto.UserRegistrationDTO;
import java.util.List;

public interface AppUserService {
    void register(UserRegistrationDTO dto);
    UserProfileDTO findByUsername(String username);
    List<UserProfileDTO> findAll();
    void updateProfileImage(String username, String imagePath);
}
