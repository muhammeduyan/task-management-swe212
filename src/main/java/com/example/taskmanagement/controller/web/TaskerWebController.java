package com.example.taskmanagement.controller.web;

import com.example.taskmanagement.dto.TaskerRequestDTO;
import com.example.taskmanagement.service.EmployeeService;
import com.example.taskmanagement.service.TaskService;
import com.example.taskmanagement.service.TaskerService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/taskers")
public class TaskerWebController {

    private final TaskerService taskerService;
    private final EmployeeService employeeService;
    private final TaskService taskService;

    public TaskerWebController(TaskerService taskerService, EmployeeService employeeService, TaskService taskService) {
        this.taskerService = taskerService;
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("taskers", taskerService.getAll());
        return "taskers/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("tasker", new TaskerRequestDTO());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("tasks", taskService.getAll());
        model.addAttribute("isEdit", false);
        return "taskers/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("tasker") TaskerRequestDTO dto,
                         BindingResult result, Model model, Authentication auth, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("tasks", taskService.getAll());
            return "taskers/form";
        }
        try {
            var created = taskerService.create(dto);
            System.out.println("[TaskerWebController] User '" + auth.getName() + "' created tasker id=" + created.getId());
            ra.addFlashAttribute("successMessage", "Task assignment created successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/taskers";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        var t = taskerService.getById(id);
        model.addAttribute("tasker", new TaskerRequestDTO(
            t.getEmployee().getId(), t.getTask().getId(), t.getTaskDate(), t.getTaskTime()
        ));
        model.addAttribute("taskerId", id);
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("tasks", taskService.getAll());
        model.addAttribute("isEdit", true);
        return "taskers/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("tasker") TaskerRequestDTO dto,
                         BindingResult result, Model model, Authentication auth, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("tasks", taskService.getAll());
            return "taskers/form";
        }
        try {
            taskerService.update(id, dto);
            System.out.println("[TaskerWebController] User '" + auth.getName() + "' updated tasker id=" + id);
            ra.addFlashAttribute("successMessage", "Task assignment updated successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/taskers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Authentication auth, RedirectAttributes ra) {
        taskerService.delete(id);
        System.out.println("[TaskerWebController] User '" + auth.getName() + "' deleted tasker id=" + id);
        ra.addFlashAttribute("successMessage", "Task assignment deleted successfully.");
        return "redirect:/taskers";
    }
}
