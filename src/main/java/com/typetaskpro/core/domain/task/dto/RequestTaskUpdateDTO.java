package com.typetaskpro.core.domain.task.dto;

import java.util.Optional;

import com.typetaskpro.core.domain.task.model.TaskStatus;

public record RequestTaskUpdateDTO(
  Optional<String> name,
  Optional<String> description,
  Optional<TaskStatus> status
) {}
