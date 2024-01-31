package com.typetaskpro.application.services;

import org.springframework.stereotype.Service;

import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.user.model.User;

@Service
public class ProjectAdministrationService {
  
  public boolean administratesProject(User user, Project project) {
    return project.getAdministrators().contains(user);
  }
}
