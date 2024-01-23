package com.typetaskpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.typetaskpro.config.security.TokenService;
import com.typetaskpro.domain.user.dto.TokenResponseDTO;
import com.typetaskpro.domain.user.dto.UserLoginDTO;
import com.typetaskpro.domain.user.dto.UserRegisterDTO;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid UserLoginDTO req) {

    var usernamePassword = new UsernamePasswordAuthenticationToken(req.username(), req.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.ok(new TokenResponseDTO(token));
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
