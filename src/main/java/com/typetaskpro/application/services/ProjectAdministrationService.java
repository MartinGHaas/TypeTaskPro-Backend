package com.typetaskpro.application.services;

import org.springframework.stereotype.Service;

import com.typetaskpro.core.cases.ProjectAdministrationUseCase;
import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.user.model.User;

@Service
public class ProjectAdministrationService implements ProjectAdministrationUseCase {

  /**
   * Checks if a user administrates a project.
   *
   * @param user the user to be checked as an administrator.
   * @param project the project to be checked by the user.
   */
  public boolean administratesProject(User user, Project project) {
    return project.getAdministrators().contains(user);
  }

  public boolean contributesToProject(User user, Project project) {
    return project.getContributors().contains(user);
  }
}
