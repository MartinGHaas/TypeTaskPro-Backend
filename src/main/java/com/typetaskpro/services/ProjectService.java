package com.typetaskpro.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.typetaskpro.domain.device.dto.ResponseDeviceDTO;
import com.typetaskpro.domain.project.dto.ResponseProjectDTO;
import com.typetaskpro.domain.project.model.Project;
import com.typetaskpro.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.domain.task.model.Task;
import com.typetaskpro.domain.user.dto.ResponseUserDTO;
import com.typetaskpro.domain.user.model.User;

@Service
public class ProjectService {
  
  public ProjectService() {
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
        getTasksDTO(project.getTasks()),
        getProjectUsers(project.getAdministrators()),
        getProjectUsers(project.getContributors())
      )
    ).toList();
  }

  public Set<ResponseTaskDTO> getTasksDTO(Set<Task> tasks) {
    return tasks.stream().map(task ->
      new ResponseTaskDTO(task.getId(), task.getName(), task.getDescription())
    ).collect(Collectors.toSet());
  }

  private List<ResponseUserDTO> getProjectUsers(Set<User> users) {
    return users.stream().map(user ->
      new ResponseUserDTO(user.getId(), user.getUsername())
    ).toList();
  }

  private ResponseDeviceDTO getProjectDeviceDTO(Project project) {
    return new ResponseDeviceDTO(
      project.getDevice().getId(),
      project.getDevice().getName()
    );
  }
}
