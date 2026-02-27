package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.TaskerRequestDTO;
import com.example.taskmanagement.dto.TaskerResponseDTO;
import com.example.taskmanagement.service.TaskerService;
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
@RequestMapping("/api/taskers")
@Tag(name = "Tasker", description = "Tasker CRUD endpoints")
public class TaskerController {

    private final TaskerService taskerService;

    public TaskerController(TaskerService taskerService) {
        this.taskerService = taskerService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create tasker assignment")
    public ResponseEntity<TaskerResponseDTO> add(@RequestBody TaskerRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskerService.create(requestDTO));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get tasker by id")
    public ResponseEntity<TaskerResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskerService.getById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all taskers")
    public ResponseEntity<List<TaskerResponseDTO>> getAll() {
        return ResponseEntity.ok(taskerService.getAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update tasker assignment")
    public ResponseEntity<TaskerResponseDTO> update(@PathVariable Integer id, @RequestBody TaskerRequestDTO requestDTO) {
        return ResponseEntity.ok(taskerService.update(id, requestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete tasker assignment")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        taskerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
