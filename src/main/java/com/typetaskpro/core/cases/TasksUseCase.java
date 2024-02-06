package com.typetaskpro.core.cases;

import java.util.Set;

import com.typetaskpro.core.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.core.domain.task.model.Task;

public interface TasksUseCase {
  
  Set<ResponseTaskDTO> getTasksDTO(Set<Task> tasks);
}
