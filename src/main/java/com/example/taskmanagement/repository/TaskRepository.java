package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("NullableProblems")
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
