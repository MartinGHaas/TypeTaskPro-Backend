package com.typetaskpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.typetaskpro.domain.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
}
