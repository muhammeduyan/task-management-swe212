package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Tasker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@SuppressWarnings("NullableProblems")
public interface TaskerRepository extends JpaRepository<Tasker, Integer> {
    boolean existsByEmployeeIdAndTaskDateAndTaskTime(Integer employeeId, LocalDate taskDate, LocalTime taskTime);
    boolean existsByEmployeeIdAndTaskDateAndTaskTimeAndIdNot(Integer employeeId, LocalDate taskDate, LocalTime taskTime, Integer id);
}
