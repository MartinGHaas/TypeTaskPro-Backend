package com.typetaskpro.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.typetaskpro.core.domain.project.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  
  @Override
  @Modifying
  @Transactional
  default void deleteById(@NonNull Long id) {
    deleteFromProjectsContributors(id);
    deleteFromProjectsAdministrators(id);
    deleteFromTasks(id);
    deleteFromProjects(id);
  }

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM projects_contributors WHERE project_id = ?1", nativeQuery = true)
  void deleteFromProjectsContributors(Long id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM projects_administrators WHERE project_id = ?1", nativeQuery = true)
  void deleteFromProjectsAdministrators(Long id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM projects WHERE id = ?1", nativeQuery = true)
  void deleteFromProjects(Long id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM tasks WHERE project_id = ?1", nativeQuery = true)
  void deleteFromTasks(Long id);
}
