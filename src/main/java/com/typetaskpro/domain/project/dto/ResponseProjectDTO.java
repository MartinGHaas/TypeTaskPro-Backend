package com.typetaskpro.domain.project.dto;

import java.util.List;
import java.util.Set;

import com.typetaskpro.domain.device.dto.ResponseDeviceDTO;
import com.typetaskpro.domain.task.dto.ResponseTaskDTO;
import com.typetaskpro.domain.user.dto.ResponseUserDTO;

public record ResponseProjectDTO(
  long id,
  String name,
  ResponseDeviceDTO device,
  Set<ResponseTaskDTO> tasks,
  List<ResponseUserDTO> administrators,
  List<ResponseUserDTO> contributors
) {}
