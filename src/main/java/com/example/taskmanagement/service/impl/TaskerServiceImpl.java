package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.dto.TaskerRequestDTO;
import com.example.taskmanagement.dto.TaskerResponseDTO;
import com.example.taskmanagement.entity.Employee;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.Tasker;
import com.example.taskmanagement.exception.BadRequestException;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.repository.EmployeeRepository;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.TaskerRepository;
import com.example.taskmanagement.service.TaskerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanagement.dto.EmployeeResponseDTO;
import com.example.taskmanagement.dto.TaskResponseDTO;

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
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.getEmployeeId()));
        Task task = taskRepository.findById(requestDTO.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + requestDTO.getTaskId()));

        Tasker tasker = new Tasker();
        tasker.setEmployee(employee);
        tasker.setTask(task);
        tasker.setTaskDate(requestDTO.getTaskDate());
        tasker.setTaskTime(requestDTO.getTaskTime());

        if (taskerRepository.existsByEmployeeIdAndTaskDateAndTaskTime(
                requestDTO.getEmployeeId(), requestDTO.getTaskDate(), requestDTO.getTaskTime())) {
            throw new BadRequestException("Employee already has a task assigned at this specific date and time.");
        }

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

        Employee employee = tasker.getEmployee();
        if (!tasker.getEmployee().getId().equals(requestDTO.getEmployeeId())) {
            employee = employeeRepository.findById(requestDTO.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + requestDTO.getEmployeeId()));
            tasker.setEmployee(employee);
        }

        Task task = tasker.getTask();
        if (!tasker.getTask().getId().equals(requestDTO.getTaskId())) {
            task = taskRepository.findById(requestDTO.getTaskId())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + requestDTO.getTaskId()));
            tasker.setTask(task);
        }

        tasker.setTaskDate(requestDTO.getTaskDate());
        tasker.setTaskTime(requestDTO.getTaskTime());

        if (taskerRepository.existsByEmployeeIdAndTaskDateAndTaskTimeAndIdNot(
                requestDTO.getEmployeeId(), requestDTO.getTaskDate(), requestDTO.getTaskTime(), id)) {
            throw new BadRequestException("Employee already has a task assigned at this specific date and time.");
        }

        Tasker updatedTasker = taskerRepository.save(tasker);
        System.out.println("Tasker updated. id=" + updatedTasker.getId() + ", employeeId=" + employee.getId() + ", taskId=" + task.getId());
        return mapToResponseDTO(updatedTasker);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Tasker tasker = taskerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasker not found with id: " + id));
        Integer employeeId = tasker.getEmployee().getId();
        Integer taskId = tasker.getTask().getId();
        taskerRepository.delete(tasker);
        System.out.println("Tasker deleted. id=" + id + ", employeeId=" + employeeId + ", taskId=" + taskId);
    }

    private TaskerResponseDTO mapToResponseDTO(Tasker tasker) {
        // 1. Employee Entity'sini EmployeeResponseDTO'ya çevir
        EmployeeResponseDTO employeeDTO = null;
        if (tasker.getEmployee() != null) {
            employeeDTO = new EmployeeResponseDTO(
                    tasker.getEmployee().getId(),
                    tasker.getEmployee().getName(),
                    tasker.getEmployee().getDepartment()
            );
        }

        // 2. Task Entity'sini TaskResponseDTO'ya çevir
        TaskResponseDTO taskDTO = null;
        if (tasker.getTask() != null) {
            taskDTO = new TaskResponseDTO(
                    tasker.getTask().getId(),
                    tasker.getTask().getName(),
                    tasker.getTask().getDescription()
            );
        }

        // 3. Hepsini TaskerResponseDTO içinde birleştir ve dışarı yolla
        return new TaskerResponseDTO(
                tasker.getId(),
                employeeDTO,
                taskDTO,
                tasker.getTaskDate(),
                tasker.getTaskTime()
        );
    }
}
