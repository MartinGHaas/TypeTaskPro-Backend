package com.typetaskpro.domain.project.dto;

import java.util.List;

import com.typetaskpro.domain.device.dto.DeviceDTO;
import com.typetaskpro.domain.user.dto.ResponseUserDTO;

public record ProjectResponseDTO(
  long id,
  String name,
  DeviceDTO device,
  List<ResponseUserDTO> administrators,
  List<ResponseUserDTO> contributors
) {}
