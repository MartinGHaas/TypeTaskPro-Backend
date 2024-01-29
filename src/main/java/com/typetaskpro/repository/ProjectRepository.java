package com.typetaskpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.typetaskpro.domain.project.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
  
  @Override
  @Modifying
  @Transactional
  default void deleteById(Long id) {
    deleteFromProjectsContributors(id);
    deleteFromProjectsAdministrators(id);
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
}