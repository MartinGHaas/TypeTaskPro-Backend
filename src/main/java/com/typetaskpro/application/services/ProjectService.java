package com.typetaskpro.application.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.typetaskpro.core.cases.ProjectUseCase;
import com.typetaskpro.core.domain.device.dto.ResponseDeviceDTO;
import com.typetaskpro.core.domain.project.dto.ResponseProjectDTO;
import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.user.dto.ResponseUserDTO;
import com.typetaskpro.core.domain.user.model.User;

@Service
public class ProjectService implements ProjectUseCase {

  private final TasksService tasksService;
  
  public ProjectService(TasksService tasksService) {
    this.tasksService = tasksService;
  }

  public void addContributors(Project project, Set<User> users) {
    project.getContributors().addAll(users);
  }

  public void addAdministrators(Project project, Set<User> users) {
    project.getContributors().addAll(users);
    project.getAdministrators().addAll(users);
  }

  public void removeContributors(Project project, Set<User> users) {
    for(User user : users) {
      project.getContributors().remove(user);
    }
  }

  public void removeAdministrators(Project project, Set<User> users) {
    for(User user : users) {
      project.getContributors().remove(user);
      project.getAdministrators().remove(user);
    }
  }

  public List<ResponseProjectDTO> getProjectPublicDTO(List<Project> projects) {
    return projects.stream().map(project -> 
      new ResponseProjectDTO(
        project.getId(),
        project.getName(),
        getProjectDeviceDTO(project),
        tasksService.getTasksDTO(project.getTasks()),
        getProjectUsers(project.getAdministrators()),
        getProjectUsers(project.getContributors())
      )
    ).toList();
  }

  protected List<ResponseUserDTO> getProjectUsers(Set<User> users) {
    return users.stream().map(user ->
      new ResponseUserDTO(user.getId(), user.getUsername())
    ).toList();
  }

  protected ResponseDeviceDTO getProjectDeviceDTO(Project project) {
    return new ResponseDeviceDTO(
      project.getDevice().getId(),
      project.getDevice().getName()
    );
  }
}
