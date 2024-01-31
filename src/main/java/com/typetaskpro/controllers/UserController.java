package com.typetaskpro.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import com.typetaskpro.application.services.UserService;
import com.typetaskpro.core.domain.user.dto.RequestPublicUserUpdateDTO;
import com.typetaskpro.core.domain.user.dto.ResponseTokenDTO;
import com.typetaskpro.core.domain.user.dto.ResponseUserDTO;
import com.typetaskpro.core.domain.user.model.User;
import com.typetaskpro.core.domain.user.model.UserRole;
import com.typetaskpro.core.repositories.UserRepository;
import com.typetaskpro.infrastructure.security.TokenService;

@RestController
@RequestMapping("/users")
public class UserController {
  
  private UserRepository userRepository;
  private TokenService tokenService;
  private UserService userService;

  public UserController(
    UserRepository userRepository,
    TokenService tokenService,
    UserService userService
  ) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {

    return ResponseEntity.ok(
      userRepository.findAll().stream()
        .map(user ->
          new ResponseUserDTO(user.getId(), user.getUsername())
        ).toList()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseUserDTO> getUser(@PathVariable long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if(!optionalUser.isPresent()) return ResponseEntity.notFound().build();
    
    User user = optionalUser.get();
    return ResponseEntity.ok(new ResponseUserDTO(id, user.getUsername()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseTokenDTO> publicUpdateUser(
    @PathVariable long id, 
    @AuthenticationPrincipal UserDetails  user,
    @RequestBody RequestPublicUserUpdateDTO req
  ) {

    Optional<User> optionalUser = userRepository.findById(id);
    if(!optionalUser.isPresent()) return ResponseEntity.badRequest().build();
    User validUser = optionalUser.get();
    
    boolean isAdmin = ((User) user).getRole() == UserRole.ADMIN;
    boolean isUserEquals = validUser.equals(user);
    if(!(isAdmin || isUserEquals))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    if(userService.usernameAlreadyExists(req.username()))
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

    validUser.setUsername(req.username());
    userRepository.save(validUser);

    if(isUserEquals) {
      return ResponseEntity.ok(
        new ResponseTokenDTO(tokenService.generateToken(validUser))
      );
    }

    return ResponseEntity.ok().build();
  }
}
