package com.example.taskmanagement.controller.web;

import com.example.taskmanagement.dto.EmployeeRequestDTO;
import com.example.taskmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;

    public EmployeeWebController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employee", new EmployeeRequestDTO());
        model.addAttribute("isEdit", false);
        return "employees/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("employee") EmployeeRequestDTO dto,
                         BindingResult result, Authentication auth, RedirectAttributes ra) {
        if (result.hasErrors()) return "employees/form";
        var created = employeeService.create(dto);
        System.out.println("[EmployeeWebController] User '" + auth.getName() + "' created employee id=" + created.getId());
        ra.addFlashAttribute("successMessage", "Employee created successfully.");
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        var emp = employeeService.getById(id);
        model.addAttribute("employee", new EmployeeRequestDTO(emp.getName(), emp.getDepartment()));
        model.addAttribute("employeeId", id);
        model.addAttribute("isEdit", true);
        return "employees/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("employee") EmployeeRequestDTO dto,
                         BindingResult result, Authentication auth, RedirectAttributes ra) {
        if (result.hasErrors()) return "employees/form";
        employeeService.update(id, dto);
        System.out.println("[EmployeeWebController] User '" + auth.getName() + "' updated employee id=" + id);
        ra.addFlashAttribute("successMessage", "Employee updated successfully.");
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Authentication auth, RedirectAttributes ra) {
        employeeService.delete(id);
        System.out.println("[EmployeeWebController] User '" + auth.getName() + "' deleted employee id=" + id);
        ra.addFlashAttribute("successMessage", "Employee deleted successfully.");
        return "redirect:/employees";
    }
}
