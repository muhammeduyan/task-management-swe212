package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.dto.UserProfileDTO;
import com.example.taskmanagement.dto.UserRegistrationDTO;
import com.example.taskmanagement.entity.AppUser;
import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.exception.BadRequestException;
import com.example.taskmanagement.repository.AppUserRepository;
import com.example.taskmanagement.repository.RoleRepository;
import com.example.taskmanagement.service.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(UserRegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BadRequestException("Username already taken: " + dto.getUsername());
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already registered: " + dto.getEmail());
        }
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));
        AppUser user = AppUser.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true)
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        System.out.println("[AppUserService] New user registered: " + dto.getUsername());
    }

    @Override
    public UserProfileDTO findByUsername(String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return mapToDTO(user);
    }

    @Override
    public List<UserProfileDTO> findAll() {
        return userRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    @Transactional
    public void updateProfileImage(String username, String imagePath) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        user.setProfileImage(imagePath);
        userRepository.save(user);
    }

    private UserProfileDTO mapToDTO(AppUser user) {
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return UserProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .roles(roles)
                .build();
    }
}
