package com.example.taskmanagement.config;

import com.example.taskmanagement.entity.AppUser;
import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.repository.AppUserRepository;
import com.example.taskmanagement.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role r = new Role(); r.setName("ADMIN"); return roleRepository.save(r);
        });
        Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
            Role r = new Role(); r.setName("USER"); return roleRepository.save(r);
        });

        if (!userRepository.existsByUsername("admin")) {
            AppUser admin = AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@example.com")
                    .enabled(true)
                    .roles(Set.of(adminRole, userRole))
                    .build();
            userRepository.save(admin);
            System.out.println("[DataInitializer] Admin user created: admin / admin123");
        }

        if (!userRepository.existsByUsername("user")) {
            AppUser user = AppUser.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .email("user@example.com")
                    .enabled(true)
                    .roles(Set.of(userRole))
                    .build();
            userRepository.save(user);
            System.out.println("[DataInitializer] Regular user created: user / user123");
        }
    }
}
