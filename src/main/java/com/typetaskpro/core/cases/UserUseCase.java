package com.typetaskpro.core.cases;

import java.util.List;
import java.util.Set;

import com.typetaskpro.core.domain.user.model.User;

public interface UserUseCase {

  boolean usernameAlreadyExists(String username);

  Set<User> getUsersFromId(List<Long> userIds);

  boolean isAdministrator(User user);
}
