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

  /**
   * Delete a project and all its references.
   *
   * @param id must not be {@literal null} and represents a project id.
   */
  @Override
  @Modifying
  @Transactional
  default void deleteById(@NonNull Long id) {
    deleteFromProjectsContributors(id);
    deleteFromProjectsAdministrators(id);
    deleteFromTasks(id);
    deleteFromProjects(id);
  }

  /**
   * Deletes a project reference from projects_contributors
   * Join Table.
   *
   * @param id The project id to be used to delete data from
   *           the Join Table.
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM projects_contributors WHERE project_id = ?1", nativeQuery = true)
  void deleteFromProjectsContributors(Long id);

  /**
   * Deletes a project reference from projects_administrators
   * Join Table.
   *
   * @param id The project id to be used to delete data from
   *           the Join Table.
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM projects_administrators WHERE project_id = ?1", nativeQuery = true)
  void deleteFromProjectsAdministrators(Long id);

  /**
   * Deletes a project from projects by its id.
   *
   * @param id The project id to be used to delete the project.
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM projects WHERE id = ?1", nativeQuery = true)
  void deleteFromProjects(Long id);

  /**
   * Deletes a task which has a project_id Join Column.
   *
   * @param id The project id to be used to delete the task.
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM tasks WHERE project_id = ?1", nativeQuery = true)
  void deleteFromTasks(Long id);
}
