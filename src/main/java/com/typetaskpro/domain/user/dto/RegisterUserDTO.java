package com.typetaskpro.domain.user.dto;

import com.typetaskpro.domain.user.model.UserRole;

public record RegisterUserDTO (String username, String password, UserRole role){
}
