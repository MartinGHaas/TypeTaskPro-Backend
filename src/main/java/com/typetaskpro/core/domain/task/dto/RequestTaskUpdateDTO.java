package com.typetaskpro.core.domain.task.dto;

import java.util.Optional;

public record RequestTaskUpdateDTO(
  Optional<String> name,
  Optional<String> description
) {}
