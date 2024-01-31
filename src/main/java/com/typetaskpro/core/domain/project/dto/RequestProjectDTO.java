package com.typetaskpro.core.domain.project.dto;

import com.typetaskpro.core.domain.device.dto.RequestDeviceDTO;

public record RequestProjectDTO(String name, RequestDeviceDTO device) {
}
