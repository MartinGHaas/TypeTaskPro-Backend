package com.typetaskpro.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.task.model.Task;

import jakarta.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String>{

  /**
   * Is used to find a project id in tasks table
   * by a task id in the application database.
   *
   * @param id The task id to be used to find a
   *           project id.
   * @return an Optional object containing a project id
   * if found in the database. Or else an Optional empty object.
   */
  @Transactional
  @Query(value = "SELECT project_id FROM tasks WHERE id = ?1", nativeQuery = true)
  Optional<Long> findProjectId(String id);
}
