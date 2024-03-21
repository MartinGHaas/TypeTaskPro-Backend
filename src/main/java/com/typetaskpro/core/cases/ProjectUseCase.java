package com.typetaskpro.core.cases;

import java.util.List;
import java.util.Set;

import com.typetaskpro.core.domain.project.dto.ResponseProjectDTO;
import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.user.model.User;

public interface ProjectUseCase {
  
  void addContributors(Project project, Set<User> users);
  
  void addAdministrators(Project project, Set<User> users);

  void removeContributors(Project project, Set<User> users);

  void removeAdministrators(Project project, Set<User> users);

  List<ResponseProjectDTO> getProjectsPublicDTO(List<Project> projects);

  ResponseProjectDTO getProjectPublicDTO(Project project);
}
