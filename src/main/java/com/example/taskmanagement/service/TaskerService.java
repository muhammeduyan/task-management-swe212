package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskerRequestDTO;
import com.example.taskmanagement.dto.TaskerResponseDTO;

import java.util.List;

public interface TaskerService {

    TaskerResponseDTO create(TaskerRequestDTO requestDTO);

    TaskerResponseDTO getById(Integer id);

    List<TaskerResponseDTO> getAll();

    TaskerResponseDTO update(Integer id, TaskerRequestDTO requestDTO);

    void delete(Integer id);
}
