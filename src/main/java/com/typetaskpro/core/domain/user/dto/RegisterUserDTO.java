package com.typetaskpro.core.domain.user.dto;

import com.typetaskpro.core.domain.user.model.UserRole;

public record RegisterUserDTO (String username, String password, UserRole role){
}
