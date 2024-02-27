package com.typetaskpro.core.domain.user.dto;

import java.util.List;

import com.typetaskpro.core.domain.project.dto.ResponseProjectDTO;
import com.typetaskpro.core.domain.user.metadata.UserMetadata;
import com.typetaskpro.core.domain.user.model.UserRole;

public record ResponseUserDataDTO(
  String username,
  UserRole role,
  List<ResponseProjectDTO> administratingProjects,
  List<ResponseProjectDTO> contributingProjects,
  List<ResponseProjectDTO> ownProjects,
  UserMetadata userMetadata
) {}
