package com.typetaskpro.domain.project.dto;

import java.util.List;
import java.util.Optional;

import com.typetaskpro.domain.device.dto.RequestDeviceDTO;

public record RequestProjectUpdateDTO(
  Optional<String> name,
  Optional<RequestDeviceDTO> device,
  Optional<List<Long>> newContributors,
  Optional<List<Long>> newAdministrators,
  Optional<List<Long>> removeContributors,
  Optional<List<Long>> removeAdministrators
) {}
