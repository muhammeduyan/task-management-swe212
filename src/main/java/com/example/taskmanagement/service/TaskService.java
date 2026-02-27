package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskRequestDTO;
import com.example.taskmanagement.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO create(TaskRequestDTO requestDTO);

    TaskResponseDTO getById(Integer id);

    List<TaskResponseDTO> getAll();

    TaskResponseDTO update(Integer id, TaskRequestDTO requestDTO);

    void delete(Integer id);
}
