package com.typetaskpro.application.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.typetaskpro.core.cases.TasksUseCase;
import com.typetaskpro.core.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.core.domain.task.model.Task;

@Service
public class TasksService implements TasksUseCase {

  public Set<ResponseTaskDTO> getTasksDTO(Set<Task> tasks) {
    return tasks.stream().map(task ->
      new ResponseTaskDTO(task.getId(), task.getName(), task.getDescription(), task.getStatus())
    ).collect(Collectors.toSet());
  }
}
