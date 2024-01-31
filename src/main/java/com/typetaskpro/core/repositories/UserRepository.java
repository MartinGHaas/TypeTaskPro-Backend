package com.typetaskpro.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
  
  UserDetails findByUsername(String username);

  User findUserByUsername(String username);
}
