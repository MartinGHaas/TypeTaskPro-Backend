package com.typetaskpro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typetaskpro.application.services.UserService;
import com.typetaskpro.core.domain.user.dto.LoginUserDTO;
import com.typetaskpro.core.domain.user.dto.RegisterUserDTO;
import com.typetaskpro.core.domain.user.dto.ResponseTokenDTO;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.domain.user.model.UserRole;
import com.typetaskpro.core.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private UserService userService;

  public AuthController(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    UserService userService
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseTokenDTO> login(@RequestBody @Valid LoginUserDTO req) {
    
    return ResponseEntity.ok(
      userService.validateAndReturnNewToken(
        req.username(),
        req.password()
      )
    );
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserDTO req) {
    
    if(userRepository.findByUsername(req.username()) != null) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    String encryptedPassword = passwordEncoder.encode(req.password());
    
    UserRole role = req.role();
    userRepository.save(new User(req.username(), encryptedPassword, role == null ? UserRole.USER : role));

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
