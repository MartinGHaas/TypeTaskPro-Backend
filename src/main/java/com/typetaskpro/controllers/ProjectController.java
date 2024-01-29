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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.domain.device.model.Device;
import com.typetaskpro.domain.project.dto.ProjectRequestDTO;
import com.typetaskpro.domain.project.dto.ProjectResponseDTO;
import com.typetaskpro.domain.project.dto.ProjectUpdateRequestDTO;
import com.typetaskpro.domain.project.model.Project;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.DeviceRepository;
import com.typetaskpro.repository.ProjectRepository;
import com.typetaskpro.repository.UserRepository;
import com.typetaskpro.services.DeviceService;
import com.typetaskpro.services.ProjectAdministrationService;
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

  @Autowired
  ProjectAdministrationService projectAdministrationService;

  @Autowired
  DeviceService deviceService;

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
  public ResponseEntity<Void> createProject(
    @RequestBody @Valid ProjectRequestDTO req,
    @AuthenticationPrincipal UserDetails userDetails
  ) {

    User user = userRepository.findUserByUsername(userDetails.getUsername());
    Device validDevice = deviceService.validateAndSaveDevice(req.device());

    Project project = new Project(req.name(), validDevice, user);

    projectRepository.save(project);
    
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(
    @PathVariable long id,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    
    Project project = projectRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    User user = (User) userDetails;
  
    if(userService.isAdministrator(user) || user.getOwnProjects().contains(project)){
        
      projectRepository.deleteById(id);
  
      return ResponseEntity.ok().build();
    }
  
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateProject(
    @PathVariable long id,
    @AuthenticationPrincipal UserDetails userDetails,
    @RequestBody ProjectUpdateRequestDTO req
  ) {

    Optional<Project> optionalProject = projectRepository.findById(id);

    if(!optionalProject.isPresent()) return ResponseEntity.badRequest().build();
    Project project = optionalProject.get();

    User requiringUser = (User) userDetails;

    boolean isAbleToRequest = projectAdministrationService.administratesProject(requiringUser, project)
                              || userService.isAdministrator(requiringUser);
    
    if(!isAbleToRequest) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    
    req.name().ifPresent(project::setName);
    req.device().ifPresent(device -> project.setDevice(deviceService.validateAndSaveDevice(device)));

    try {
      req.newContributors().ifPresent(contributorsId ->
        projectService.addContributors(project, userService.getUsersFromId(contributorsId))
      );
      req.newAdministrators().ifPresent(administratorsId -> 
        projectService.addAdministrators(project, userService.getUsersFromId(administratorsId))
      );
      req.removeContributors().ifPresent(contributorsId -> 
        projectService.removeContributors(project, userService.getUsersFromId(contributorsId))
      );
      req.removeAdministrators().ifPresent(administratorsId -> 
        projectService.removeContributors(project, userService.getUsersFromId(administratorsId))
      );

    } catch(ResponseStatusException responseStatusException) {
      return ResponseEntity.badRequest().build();
    }

    projectRepository.save(project);
    
    return ResponseEntity.ok().build();
  }
}
