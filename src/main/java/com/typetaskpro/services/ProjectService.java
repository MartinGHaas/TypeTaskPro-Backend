package com.typetaskpro.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.typetaskpro.domain.device.dto.DeviceDTO;
import com.typetaskpro.domain.project.dto.ProjectResponseDTO;
import com.typetaskpro.domain.project.model.Project;
import com.typetaskpro.domain.user.dto.UserResponseDTO;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.repository.ProjectRepository;

@Service
public class ProjectService {
  
  @Autowired
  ProjectRepository projectRepository;

  public List<ProjectResponseDTO> getProjectPublicDTO(List<Project> projects) {
    return projects.stream().map(project -> 
      new ProjectResponseDTO(
        project.getId(),                
        project.getName(),
        getProjectDeviceDTO(project),
        getProjectUsers(project.getAdministrators()),
        getProjectUsers(project.getContributors())
      )
    ).toList();
  }

  private List<UserResponseDTO> getProjectUsers(Set<User> users) {
    return users.stream().map(user ->
      new UserResponseDTO(user.getId(), user.getUsername())
    ).toList();
  }

  private DeviceDTO getProjectDeviceDTO(Project project) {
    return new DeviceDTO(
      project.getDevice().getId(),
      project.getDevice().getName()
    );
  }
}
