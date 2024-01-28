package com.typetaskpro.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.config.security.TokenService;
import com.typetaskpro.domain.user.dto.TokenResponseDTO;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.UserRepository;

@Service
public class UserService {
  
  @Autowired
  UserRepository userRepository;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  TokenService tokenService;
  
  public boolean usernameAlreadyExists(String username) {
    return userRepository.findByUsername(username) != null;
  }

  public Set<User> getUsersFromId(List<Long> userIds) {
    if(userIds != null && !userIds.contains(null))
      return new HashSet<>(userRepository.findAllById(userIds));

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
  }

  public TokenResponseDTO validateAndReturnNewToken(String username, String password) {
    
    var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
    Authentication auth = authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return new TokenResponseDTO(token);
  }

  public boolean isAdministrator(User user) {
    return user.getRole() == UserRole.ADMIN;
  }
}
