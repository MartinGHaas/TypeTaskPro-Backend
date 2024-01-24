package com.typetaskpro.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.typetaskpro.config.security.TokenService;
import com.typetaskpro.domain.user.dto.PublicUserUpdateDTO;
import com.typetaskpro.domain.user.dto.TokenResponseDTO;
import com.typetaskpro.domain.user.dto.UserResponseDTO;
import com.typetaskpro.domain.user.model.User;
import com.typetaskpro.domain.user.model.UserRole;
import com.typetaskpro.repository.UserRepository;
import com.typetaskpro.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
  
  @Autowired
  UserRepository userRepository;

  @Autowired
  TokenService tokenService;

  @Autowired
  UserService userService;

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

    return ResponseEntity.ok(
      userRepository.findAll().stream()
        .map(user ->
          new UserResponseDTO(user.getId(), user.getUsername())
        ).toList()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if(!optionalUser.isPresent()) return ResponseEntity.notFound().build();
    
    User user = optionalUser.get();
    return ResponseEntity.ok(new UserResponseDTO(id, user.getUsername()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TokenResponseDTO> publicUpdateUser(
    @PathVariable long id, 
    @AuthenticationPrincipal UserDetails  user,
    @RequestBody PublicUserUpdateDTO req
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
        new TokenResponseDTO(tokenService.generateToken(validUser))
      );
    }

    return ResponseEntity.ok().build();
  }
}
