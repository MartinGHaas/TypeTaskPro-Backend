package com.typetaskpro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typetaskpro.domain.user.dto.TokenResponseDTO;
import com.typetaskpro.domain.user.dto.UserLoginDTO;
import com.typetaskpro.domain.user.dto.UserRegisterDTO;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.UserRepository;
import com.typetaskpro.services.UserService;

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
  public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid UserLoginDTO req) {
    
    return ResponseEntity.ok(
      userService.validateAndReturnNewToken(
        req.username(),
        req.password()
      )
    );
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDTO req) {
    
    if(userRepository.findByUsername(req.username()) != null) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    String encryptedPassword = passwordEncoder.encode(req.password());
    
    UserRole role = req.role();
    userRepository.save(new User(req.username(), encryptedPassword, role == null ? UserRole.USER : role));

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
