package com.typetaskpro.domain.project.dto;

import java.util.List;

import com.typetaskpro.domain.device.dto.ResponseDeviceDTO;
import com.typetaskpro.domain.user.dto.ResponseUserDTO;

public record RsponseProjectDTO(
  long id,
  String name,
  ResponseDeviceDTO device,
  List<ResponseUserDTO> administrators,
  List<ResponseUserDTO> contributors
) {}
