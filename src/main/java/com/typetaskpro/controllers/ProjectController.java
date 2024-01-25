package com.typetaskpro.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.typetaskpro.domain.device.model.Device;
import com.typetaskpro.domain.project.dto.ProjectRequestDTO;
import com.typetaskpro.domain.project.dto.ProjectResponseDTO;
import com.typetaskpro.domain.project.model.Project;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.DeviceRepository;
import com.typetaskpro.repository.ProjectRepository;
import com.typetaskpro.repository.UserRepository;
import com.typetaskpro.services.ProjectService;
import com.typetaskpro.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
public class ProjectController {
  
  @Autowired
  ProjectRepository projectRepository;

  @Autowired 
  ProjectService projectService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  DeviceRepository deviceRepository;

  @Autowired
  UserService userService;

  @GetMapping
  public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(
    @RequestParam(value = "userId", defaultValue = "-1") long id,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    
    User user = (User) userDetails;
    
    if(id < 0) {

      if(user.getRole() == UserRole.ADMIN) {

        return ResponseEntity.ok(
          projectService.getProjectPublicDTO(projectRepository.findAll())
        );
      }
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    };

    return ResponseEntity.ok(projectService.getProjectPublicDTO(
      user.getContributingProjects()
    ));
  }

  @PostMapping
  public ResponseEntity<Void> createrProject(
    @RequestBody @Valid ProjectRequestDTO req,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    User user = userRepository.findUserByUsername(userDetails.getUsername());
    
    Optional<Device> optionalDevice = deviceRepository.findByName(req.device().name());

    Device validDevice;
    if(!optionalDevice.isPresent()) {
      validDevice = new Device(req.device().name());
      deviceRepository.save(validDevice);
    } else {
      // WTF ELSE ????????????????????????
      validDevice = optionalDevice.get();
    }

    Project project = new Project(req.name(), validDevice, user);

    projectRepository.save(project);

    user.getAdministratingProjects().add(project);
    user.getContributingProjects().add(project);
    userRepository.save(user);    
    
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/id")
  public ResponseEntity<Void> deleteProject(
    @PathVariable long id,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    
    Optional<Project> project = projectRepository.findById(id);

    if(!project.isPresent()) return ResponseEntity.badRequest().build();

    User user = (User) userDetails;

    if(userService.isAdministrator(user) || user.equals(projectService.getProjectOwner(project.get())))
      return ResponseEntity.ok().build();

    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
