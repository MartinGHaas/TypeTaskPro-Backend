package com.typetaskpro.core.cases;

import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.user.model.User;

public interface ProjectAdministrationUseCase {
  
  boolean administratesProject(User user, Project project);
}
