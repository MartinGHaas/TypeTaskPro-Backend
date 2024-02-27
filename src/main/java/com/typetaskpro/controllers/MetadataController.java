package com.typetaskpro.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.application.services.UserService;
import com.typetaskpro.core.domain.user.metadata.UserMetadata;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.repositories.UserMetadataRepository;
import com.typetaskpro.core.repositories.UserRepository;

@RestController
@RequestMapping("metadata")
public class MetadataController {

  private final UserMetadataRepository userMetadataRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public MetadataController(
    UserMetadataRepository userMetadataRepository,
    UserRepository userRepository,
    UserService userService
  ) {
    this.userMetadataRepository = userMetadataRepository;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<UserMetadata> getUserMetadata(
    @PathVariable long id,
    @AuthenticationPrincipal UserDetails userDetails
  ) {

    User user = userRepository.findUserByUsername(userDetails.getUsername())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

    User idUser = userRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

      if(!userService.isAdministrator(user)) {

        if(!user.equals(idUser)) {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
      }

    UserMetadata metadata = userMetadataRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return ResponseEntity.ok(metadata);
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<Void> updateUserMetadata(
    @RequestParam(value = "total-projects") Optional<Integer> totalProjects,
    @RequestParam(value = "projects-in-development") Optional<Integer> projectsInDevelopment,
    @RequestParam(value = "projects-completed") Optional<Integer> projectsCompleted,
    @RequestParam(value = "total-tasks") Optional<Integer> totalTasks,
    @RequestParam(value = "tasks-in-progress") Optional<Integer> tasksInProgress,
    @RequestParam(value = "tasks-todo") Optional<Integer> tasksToDo,
    @RequestParam(value = "tasks-completed") Optional<Integer> tasksCompleted,
    @RequestParam(value = "touched-project") Optional<Long> lastTouchedProject,
    @RequestParam(value = "touched-task") Optional<String> lastTouchedTask,
    @AuthenticationPrincipal UserDetails userDetails,
    @PathVariable long id
  ) {
    User user = userRepository.findUserByUsername(userDetails.getUsername())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

    User idUser = userRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if(!userService.isAdministrator(user)) {

      if(!user.equals(idUser)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
      }
    }

    totalProjects.ifPresent((metadata) -> {
      userMetadataRepository.updateTotalProjects(id, metadata);
    });

    projectsInDevelopment.ifPresent((metadata) -> {
      userMetadataRepository.updateProjectsInDevelopment(id, metadata);
    });

    projectsCompleted.ifPresent((metadata) -> {
      userMetadataRepository.updateProjectsCompleted(id, metadata);
    });

    totalTasks.ifPresent((metadata) -> {
      userMetadataRepository.updateTotalTasks(id, metadata);
    });

    tasksInProgress.ifPresent((metadata) -> {
      userMetadataRepository.updateTasksInProgress(id, metadata);
    });

    tasksToDo.ifPresent((metadata) -> {
      userMetadataRepository.updateTasksTodo(id, metadata);
    });

    tasksCompleted.ifPresent((metadata) -> {
      userMetadataRepository.updateTasksCompleted(id, metadata);
    });

    lastTouchedProject.ifPresent((metadata) -> {
      userMetadataRepository.updateLastTouchedProject(id, metadata);
    });

    lastTouchedTask.ifPresent((metadata) -> {
      userMetadataRepository.updateLastTouchedTask(id, metadata);
    });
    
    return ResponseEntity.ok().build();
  }
}