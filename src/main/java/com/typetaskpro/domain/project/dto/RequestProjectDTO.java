package com.typetaskpro.domain.project.dto;

import com.typetaskpro.domain.device.dto.RequestDeviceDTO;

public record RequestProjectDTO(String name, RequestDeviceDTO device) {
}
