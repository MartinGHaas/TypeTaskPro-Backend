package com.typetaskpro.application.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.typetaskpro.core.cases.TasksUseCase;
import com.typetaskpro.core.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.core.domain.task.model.Task;

@Service
public class TasksService implements TasksUseCase {

  /**
   * Transform a set of tasks in a set of
   * tasks responses.
   *
   * @param tasks is a set of tasks.
   * @return a set of responses of tasks.
   */
  public Set<ResponseTaskDTO> getTasksDTO(Set<Task> tasks) {
    return tasks.stream().map(task ->
      new ResponseTaskDTO(task.getId(), task.getName(), task.getDescription(), task.getStatus())
    ).collect(Collectors.toSet());
  }
}
