package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("NullableProblems")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
