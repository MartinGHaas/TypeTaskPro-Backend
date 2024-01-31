package com.typetaskpro.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.application.services.ProjectAdministrationService;
import com.typetaskpro.application.services.ProjectService;
import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.task.dto.RequestTaskDTO;
import com.typetaskpro.core.domain.task.dto.RequestTaskUpdateDTO;
import com.typetaskpro.core.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.core.domain.task.model.Task;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.domain.user.model.UserRole;
import com.typetaskpro.core.repositories.ProjectRepository;
import com.typetaskpro.core.repositories.TaskRepository;
import com.typetaskpro.core.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class TaskController {
  
  private ProjectRepository projectRepository;
  private ProjectAdministrationService projectAdministrationService;
  private ProjectService projectService;
  private TaskRepository taskRepository;
  private UserRepository userRepository;

  public TaskController(
    ProjectRepository projectRepository,
    ProjectAdministrationService projectAdministrationService,
    ProjectService projectService,
    TaskRepository taskRepository,
    UserRepository userRepository
  ) {
    this.projectRepository = projectRepository;
    this.projectAdministrationService = projectAdministrationService;
    this.projectService = projectService;
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("projects/{projectId}/tasks")
  public ResponseEntity<Set<ResponseTaskDTO>> getAllProjectTasks(
    @PathVariable @Valid long projectId,
    @AuthenticationPrincipal UserDetails user
  ) {

    Project project = projectRepository.findById(projectId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
    boolean isAbleToRequest = userRepository
      .findUserByUsername(user.getUsername())
      .getContributingProjects()
      .contains(project);

    if(!isAbleToRequest) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(projectService.getTasksDTO(project.getTasks()));
  }

  @PostMapping("/projects/{projectId}/tasks")
  public ResponseEntity<Void> createTask(
    @PathVariable long projectId,
    @RequestBody RequestTaskDTO req
  ) {

    Project project = projectRepository.findById(projectId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    Task task = new Task(req.name(), req.description());
    project.getTasks().add(task);

    taskRepository.save(task);
    projectRepository.save(project);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/tasks/{id}")
  public ResponseEntity<Void> updateTask(
    @PathVariable @NonNull @Valid String id,
    @AuthenticationPrincipal User user,
    @RequestBody RequestTaskUpdateDTO req
  ) {

    Task task = taskRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    req.name().ifPresent(task::setName);
    req.description().ifPresent(task::setDescription);

    taskRepository.save(task);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity<Void> deleteTask(
    @PathVariable @NonNull String id,
    @AuthenticationPrincipal User user
  ) {

    taskRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
    long project_id = taskRepository.findProjectId(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    boolean isAbleToRequest = projectAdministrationService.administratesProject(user, projectRepository.findById(project_id).get())
      || user.getRole() == UserRole.ADMIN;

    if(isAbleToRequest) {

      taskRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    
    return ResponseEntity.notFound().build();
  }
}