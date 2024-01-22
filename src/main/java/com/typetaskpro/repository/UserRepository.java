package com.typetaskpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.typetaskpro.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
  
  UserDetails findByUsername(String username);
}
