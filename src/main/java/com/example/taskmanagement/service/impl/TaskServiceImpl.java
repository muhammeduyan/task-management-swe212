package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.dto.TaskRequestDTO;
import com.example.taskmanagement.dto.TaskResponseDTO;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public TaskResponseDTO create(TaskRequestDTO requestDTO) {
        Task task = new Task();
        task.setName(requestDTO.name());
        task.setDescription(requestDTO.description());
        Task savedTask = taskRepository.save(task);
        System.out.println("Task created. id=" + savedTask.getId() + ", name=" + savedTask.getName());
        return mapToResponseDTO(savedTask);
    }

    @Override
    public TaskResponseDTO getById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return mapToResponseDTO(task);
    }

    @Override
    public List<TaskResponseDTO> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public TaskResponseDTO update(Integer id, TaskRequestDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setName(requestDTO.name());
        task.setDescription(requestDTO.description());
        Task updatedTask = taskRepository.save(task);
        System.out.println("Task updated. id=" + updatedTask.getId() + ", name=" + updatedTask.getName());
        return mapToResponseDTO(updatedTask);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
        System.out.println("Task deleted. id=" + id);
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        return new TaskResponseDTO(task.getId(), task.getName(), task.getDescription());
    }
}
