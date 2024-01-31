package com.typetaskpro.core.domain.project.dto;

import java.util.List;
import java.util.Set;

import com.typetaskpro.core.domain.device.dto.ResponseDeviceDTO;
import com.typetaskpro.core.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.core.domain.user.dto.ResponseUserDTO;

public record ResponseProjectDTO(
  long id,
  String name,
  ResponseDeviceDTO device,
  Set<ResponseTaskDTO> tasks,
  List<ResponseUserDTO> administrators,
  List<ResponseUserDTO> contributors
) {}
