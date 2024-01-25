package com.typetaskpro.domain.project.dto;

import com.typetaskpro.domain.device.dto.DeviceRequestDTO;

public record ProjectRequestDTO(String name, DeviceRequestDTO device) {
}
