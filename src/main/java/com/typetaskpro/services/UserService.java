package com.typetaskpro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
