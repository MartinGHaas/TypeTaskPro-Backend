package com.typetaskpro.core.domain.task.dto;

import com.typetaskpro.core.domain.task.model.TaskStatus;

public record ResponseTaskDTO(
  String id,
  String name,
  String description,
  TaskStatus status
) {}
