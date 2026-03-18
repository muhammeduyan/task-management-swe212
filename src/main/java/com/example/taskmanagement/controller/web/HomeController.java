package com.example.taskmanagement.controller.web;

import com.example.taskmanagement.dto.UserProfileDTO;
import com.example.taskmanagement.dto.UserRegistrationDTO;
import com.example.taskmanagement.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
public class HomeController {

    private final AppUserService userService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public HomeController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) return "redirect:/dashboard";
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        if (auth != null) {
            UserProfileDTO profile = userService.findByUsername(auth.getName());
            model.addAttribute("user", profile);
        }
        return "dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registrationDto", new UserRegistrationDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO dto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.register(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        UserProfileDTO profile = userService.findByUsername(auth.getName());
        model.addAttribute("user", profile);
        return "users/profile";
    }

    @PostMapping("/profile/upload-image")
    public String uploadImage(@RequestParam("image") MultipartFile file,
                               Authentication auth,
                               RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a file to upload.");
            return "redirect:/profile";
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid file name.");
            return "redirect:/profile";
        }
        // Sanitize filename: keep only the last path component and replace unsafe characters
        String sanitized = Paths.get(originalFilename).getFileName().toString()
                .replaceAll("[^a-zA-Z0-9._-]", "_");
        try {
            String filename = UUID.randomUUID() + "_" + sanitized;
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            Path target = uploadPath.resolve(filename).normalize();
            // Ensure the resolved path is still within the upload directory
            if (!target.startsWith(uploadPath)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid file path.");
                return "redirect:/profile";
            }
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            userService.updateProfileImage(auth.getName(), "/uploads/" + filename);
            System.out.println("[HomeController] User '" + auth.getName() + "' uploaded profile image: " + filename);
            redirectAttributes.addFlashAttribute("successMessage", "Profile image updated successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload image: " + e.getMessage());
        }
        return "redirect:/profile";
    }
}
