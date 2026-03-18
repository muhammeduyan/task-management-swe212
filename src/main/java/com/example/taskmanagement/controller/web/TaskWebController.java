package com.example.taskmanagement.controller.web;

import com.example.taskmanagement.dto.TaskRequestDTO;
import com.example.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskWebController {

    private final TaskService taskService;

    public TaskWebController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", taskService.getAll());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("task", new TaskRequestDTO());
        model.addAttribute("isEdit", false);
        return "tasks/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("task") TaskRequestDTO dto,
                         BindingResult result, Authentication auth, RedirectAttributes ra) {
        if (result.hasErrors()) return "tasks/form";
        var created = taskService.create(dto);
        System.out.println("[TaskWebController] User '" + auth.getName() + "' created task id=" + created.getId());
        ra.addFlashAttribute("successMessage", "Task created successfully.");
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        var task = taskService.getById(id);
        model.addAttribute("task", new TaskRequestDTO(task.getName(), task.getDescription()));
        model.addAttribute("taskId", id);
        model.addAttribute("isEdit", true);
        return "tasks/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("task") TaskRequestDTO dto,
                         BindingResult result, Authentication auth, RedirectAttributes ra) {
        if (result.hasErrors()) return "tasks/form";
        taskService.update(id, dto);
        System.out.println("[TaskWebController] User '" + auth.getName() + "' updated task id=" + id);
        ra.addFlashAttribute("successMessage", "Task updated successfully.");
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Authentication auth, RedirectAttributes ra) {
        taskService.delete(id);
        System.out.println("[TaskWebController] User '" + auth.getName() + "' deleted task id=" + id);
        ra.addFlashAttribute("successMessage", "Task deleted successfully.");
        return "redirect:/tasks";
    }
}
