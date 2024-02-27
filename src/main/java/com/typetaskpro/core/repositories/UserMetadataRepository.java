package com.typetaskpro.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.user.metadata.UserMetadata;

import jakarta.transaction.Transactional;

@Repository
public interface UserMetadataRepository extends JpaRepository<UserMetadata, Long> {
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET total_projects = ?2 WHERE id = ?1", nativeQuery = true)
  void updateTotalProjects(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET projects_in_development = ?2 WHERE id = ?1", nativeQuery = true)
  void updateProjectsInDevelopment(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET projects_completed = ?2 WHERE id = ?1", nativeQuery = true)
  void updateProjectsCompleted(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET total_tasks = ?2 WHERE id = ?1", nativeQuery = true)
  void updateTotalTasks(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET tasks_in_progress = ?2 WHERE id = ?1", nativeQuery = true)
  void updateTasksInProgress(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET tasks_todo = ?2 WHERE id = ?1", nativeQuery = true)
  void updateTasksTodo(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET tasks_completed = ?2 WHERE id = ?1", nativeQuery = true)
  void updateTasksCompleted(long id, int newValue);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET last_touched_project_id = ?2 WHERE id = ?1", nativeQuery = true)
  void updateLastTouchedProject(long id, long touchedProjectId);
  
  @Transactional
  @Modifying
  @Query(value = "UPDATE user_metadata SET last_touched_task_id = '?2' WHERE id = ?1", nativeQuery = true)
  void updateLastTouchedTask(long id, String touchedTaskId);
}
