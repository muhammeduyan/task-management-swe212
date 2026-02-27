package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Tasker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskerRepository extends JpaRepository<Tasker, Integer> {
}
