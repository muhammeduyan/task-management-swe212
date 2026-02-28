package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.EmployeeRequestDTO;
import com.example.taskmanagement.dto.EmployeeResponseDTO;
import com.example.taskmanagement.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/employees")
@Tag(name = "Employee", description = "Employee CRUD endpoints")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create employee")
    public ResponseEntity<EmployeeResponseDTO> add(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(requestDTO));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get employee by id")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all employees")
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update employee")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Integer id,@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return ResponseEntity.ok(employeeService.update(id, requestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete employee")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
