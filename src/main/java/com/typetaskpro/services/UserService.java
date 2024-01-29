package com.typetaskpro.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.config.security.TokenService;
import com.typetaskpro.domain.user.dto.ResponseTokenDTO;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.UserRepository;

@Service
public class UserService {
  
  private UserRepository userRepository;
  private AuthenticationManager authenticationManager;
  private TokenService tokenService;
  
  public UserService(
    UserRepository userRepository,
    AuthenticationManager authenticationManager,
    TokenService tokenService
  ) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  public boolean usernameAlreadyExists(String username) {
    return userRepository.findByUsername(username) != null;
  }

  public Set<User> getUsersFromId(List<Long> userIds) {
    if(userIds != null && !userIds.contains(null))
      return new HashSet<>(userRepository.findAllById(userIds));

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
  }

  public ResponseTokenDTO validateAndReturnNewToken(String username, String password) {
    
    var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
    Authentication auth = authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return new ResponseTokenDTO(token);
  }

  public boolean isAdministrator(User user) {
    return user.getRole() == UserRole.ADMIN;
  }
}
