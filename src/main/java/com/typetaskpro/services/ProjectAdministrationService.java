package com.typetaskpro.services;

import org.springframework.stereotype.Service;

import com.typetaskpro.domain.project.model.Project;
import com.typetaskpro.domain.user.model.User;

@Service
public class ProjectAdministrationService {
  
  public boolean administratesProject(User user, Project project) {
    return project.getAdministrators().contains(user);
  }

  public User getProjectOwner(Project project) {
    return project.getAdministrators().get(0);
  }
}
