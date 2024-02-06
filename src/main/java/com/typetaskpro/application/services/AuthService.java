package com.typetaskpro.application.services;

import jakarta.inject.Provider;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.core.cases.AuthUseCase;
import com.typetaskpro.core.domain.user.dto.ResponseTokenDTO;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.domain.user.model.UserRole;
import com.typetaskpro.core.repositories.UserRepository;
import com.typetaskpro.infrastructure.security.TokenService;

@Service
public class AuthService implements UserDetailsService, AuthUseCase {

  private final UserRepository userRepository;
  private final Provider<AuthenticationManager> authenticationManager;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(
    UserRepository userRepository,
    Provider<AuthenticationManager> authenticationManager,
    TokenService tokenService,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * @param username the username identifying the user whose data is required.
   * @return UserDetails object.
   * @throws UsernameNotFoundException if the username wasn't found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
  }

  /**
   * Validates username and password and returns a new JWT Token.
   *
   * @param username of the User.
   * @param password of the User.
   * @return the JWT Token as a ResponseTokenDTO.
   */
  @Override
  public ResponseTokenDTO validateAndReturnNewToken(String username, String password) {
    
    var usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
    Authentication auth = authenticationManager.get().authenticate(usernamePassword);

    var token = tokenService.generateToken(((User) auth.getPrincipal()).getUsername());

    return new ResponseTokenDTO(token);
  }

  /**
   * Register a new user in the application database.
   *
   * @param username of the User.
   * @param password not encrypted of the User.
   * @param role the User Role or null.
   */
  @Override
  public void registerNewUser(String username, String password, UserRole role) {
    
    String encryptedPassword = passwordEncoder.encode(password);
    
    userRepository.save(
      new User(username, encryptedPassword, validateRoleOrDefault(role))
    );
  }

  /**
   * Validates the existence of a user role or defines
   * a 'User' role as default.
   *
   * @param role UserRole object to be validated.
   * @return role if present or a 'User' role as default.
   */
  private UserRole validateRoleOrDefault(UserRole role) {
    return role == null ? UserRole.USER : role;
  }
}
