package com.typetaskpro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.typetaskpro.application.services.AuthService;
import com.typetaskpro.core.domain.user.dto.LoginUserDTO;
import com.typetaskpro.core.domain.user.dto.RegisterUserDTO;
import com.typetaskpro.core.domain.user.dto.ResponseTokenDTO;
import com.typetaskpro.core.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  private UserRepository userRepository;
  private AuthService authService;

  public AuthController(
    UserRepository userRepository,
    AuthService authService
  ) {
    this.userRepository = userRepository;
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseTokenDTO> login(@RequestBody @Valid LoginUserDTO req) {
    
    return ResponseEntity.ok(
      authService.validateAndReturnNewToken(
        req.username(),
        req.password()
      )
    );
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserDTO req) {
    
    userRepository.findByUsername(req.username()).ifPresent((user) -> {
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
    });

    authService.registerNewUser(req.username(), req.password(), req.role());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
