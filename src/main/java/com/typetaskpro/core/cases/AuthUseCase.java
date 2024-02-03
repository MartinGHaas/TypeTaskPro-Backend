package com.typetaskpro.core.cases;

import com.typetaskpro.core.domain.user.dto.ResponseTokenDTO;
import com.typetaskpro.core.domain.user.model.UserRole;

public interface AuthUseCase {

  ResponseTokenDTO validateAndReturnNewToken(String username, String password);

  void registerNewUser(String username, String password, UserRole role);
}
