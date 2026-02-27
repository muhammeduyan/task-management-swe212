package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.TaskRequestDTO;
import com.example.taskmanagement.dto.TaskResponseDTO;
import com.example.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task", description = "Task CRUD endpoints")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create task")
    public ResponseEntity<TaskResponseDTO> add(@RequestBody TaskRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(requestDTO));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all tasks")
    public ResponseEntity<List<TaskResponseDTO>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update task")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Integer id, @RequestBody TaskRequestDTO requestDTO) {
        return ResponseEntity.ok(taskService.update(id, requestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
