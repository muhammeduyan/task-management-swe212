package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.dto.EmployeeRequestDTO;
import com.example.taskmanagement.dto.EmployeeResponseDTO;
import com.example.taskmanagement.entity.Employee;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.EmployeeRepository;
import com.example.taskmanagement.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public EmployeeResponseDTO create(EmployeeRequestDTO requestDTO) {
        Employee employee = new Employee();
        employee.setName(requestDTO.name());
        employee.setDepartment(requestDTO.department());
        Employee savedEmployee = employeeRepository.save(employee);
        System.out.println("Employee created. id=" + savedEmployee.getId() + ", name=" + savedEmployee.getName());
        return mapToResponseDTO(savedEmployee);
    }

    @Override
    public EmployeeResponseDTO getById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponseDTO update(Integer id, EmployeeRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employee.setName(requestDTO.name());
        employee.setDepartment(requestDTO.department());
        Employee updatedEmployee = employeeRepository.save(employee);
        System.out.println("Employee updated. id=" + updatedEmployee.getId() + ", name=" + updatedEmployee.getName());
        return mapToResponseDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
        System.out.println("Employee deleted. id=" + id);
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
        return new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getDepartment());
    }
}
