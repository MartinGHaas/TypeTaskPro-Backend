package com.typetaskpro.application.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.core.cases.UserUseCase;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.domain.user.model.UserRole;
import com.typetaskpro.core.repositories.UserRepository;

@Service
public class UserService implements UserUseCase {

  private UserRepository userRepository;
  
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean usernameAlreadyExists(String username) {
    return userRepository.findByUsername(username) != null;
  }

  public Set<User> getUsersFromId(List<Long> userIds) {
    if(userIds != null && !userIds.contains(null))
      return new HashSet<>(userRepository.findAllById(userIds));

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
  }

  public boolean isAdministrator(User user) {
    return user.getRole() == UserRole.ADMIN;
  }
}
