package com.typetaskpro.domain.project.dto;

import java.util.List;
import java.util.Optional;

import com.typetaskpro.domain.device.dto.DeviceRequestDTO;

public record ProjectUpdateRequestDTO(
  Optional<String> name,
  Optional<DeviceRequestDTO> device,
  Optional<List<Long>> contributors,
  Optional<List<Long>> administrators
) {}
