package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.dto.TaskerRequestDTO;
import com.example.taskmanagement.dto.TaskerResponseDTO;
import com.example.taskmanagement.entity.Employee;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.Tasker;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.EmployeeRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.TaskerRepository;
import com.example.taskmanagement.service.TaskerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskerServiceImpl implements TaskerService {

    private final TaskerRepository taskerRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public TaskerServiceImpl(TaskerRepository taskerRepository,
                             EmployeeRepository employeeRepository,
                             TaskRepository taskRepository) {
        this.taskerRepository = taskerRepository;
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public TaskerResponseDTO create(TaskerRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(requestDTO.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.employeeId()));
        Task task = taskRepository.findById(requestDTO.taskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + requestDTO.taskId()));

        Tasker tasker = new Tasker();
        tasker.setEmployee(employee);
        tasker.setTask(task);
        tasker.setTaskDate(requestDTO.taskDate());
        tasker.setTaskTime(requestDTO.taskTime());

        Tasker savedTasker = taskerRepository.save(tasker);
        System.out.println("Tasker created. id=" + savedTasker.getId() + ", employeeId=" + employee.getId() + ", taskId=" + task.getId());
        return mapToResponseDTO(savedTasker);
    }

    @Override
    public TaskerResponseDTO getById(Integer id) {
        Tasker tasker = taskerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasker not found with id: " + id));
        return mapToResponseDTO(tasker);
    }

    @Override
    public List<TaskerResponseDTO> getAll() {
        return taskerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public TaskerResponseDTO update(Integer id, TaskerRequestDTO requestDTO) {
        Tasker tasker = taskerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasker not found with id: " + id));
        Employee employee = employeeRepository.findById(requestDTO.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.employeeId()));
        Task task = taskRepository.findById(requestDTO.taskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + requestDTO.taskId()));

        tasker.setEmployee(employee);
        tasker.setTask(task);
        tasker.setTaskDate(requestDTO.taskDate());
        tasker.setTaskTime(requestDTO.taskTime());

        Tasker updatedTasker = taskerRepository.save(tasker);
        System.out.println("Tasker updated. id=" + updatedTasker.getId() + ", employeeId=" + employee.getId() + ", taskId=" + task.getId());
        return mapToResponseDTO(updatedTasker);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Tasker tasker = taskerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasker not found with id: " + id));
        taskerRepository.delete(tasker);
        System.out.println("Tasker deleted. id=" + id);
    }

    private TaskerResponseDTO mapToResponseDTO(Tasker tasker) {
        return new TaskerResponseDTO(
                tasker.getId(),
                tasker.getEmployee() != null ? tasker.getEmployee().getId() : null,
                tasker.getTask() != null ? tasker.getTask().getId() : null,
                tasker.getTaskDate(),
                tasker.getTaskTime()
        );
    }
}
