package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.EmployeeRequestDTO;
import com.example.taskmanagement.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO create(EmployeeRequestDTO requestDTO);

    EmployeeResponseDTO getById(Integer id);

    List<EmployeeResponseDTO> getAll();

    EmployeeResponseDTO update(Integer id, EmployeeRequestDTO requestDTO);

    void delete(Integer id);
}
