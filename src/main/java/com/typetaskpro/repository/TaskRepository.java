package com.typetaskpro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.typetaskpro.domain.task.model.Task;

import jakarta.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String>{

  @Transactional
  @Query(value = "SELECT project_id FROM tasks WHERE id = ?1", nativeQuery = true)
  Optional<Long> findProjectId(String id);
}
