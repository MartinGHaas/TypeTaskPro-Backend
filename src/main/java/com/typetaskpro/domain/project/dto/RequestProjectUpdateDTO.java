package com.typetaskpro.domain.project.dto;

import java.util.List;
import java.util.Optional;

import com.typetaskpro.domain.device.dto.DeviceRequestDTO;

public record RequestProjectUpdateDTO(
  Optional<String> name,
  Optional<DeviceRequestDTO> device,
  Optional<List<Long>> newContributors,
  Optional<List<Long>> newAdministrators,
  Optional<List<Long>> removeContributors,
  Optional<List<Long>> removeAdministrators
) {}
