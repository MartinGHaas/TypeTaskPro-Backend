package com.typetaskpro.domain.user.dto;

import com.typetaskpro.domain.user.model.UserRole;

public record UserRegisterDTO (String username, String password, UserRole role){
}
