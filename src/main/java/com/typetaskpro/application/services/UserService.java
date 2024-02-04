package com.typetaskpro.application.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.domain.user.model.UserRole;
import com.typetaskpro.core.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
  
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

  @Transactional
  public byte[] getUserProfilePictureData(long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return user.getProfilePicture().getData();
  }
}
