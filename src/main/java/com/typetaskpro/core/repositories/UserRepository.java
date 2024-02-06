package com.typetaskpro.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

  /**
   * Is used to find a UserDetails object
   * by its username in the application database.
   *
   * @param username to be used to find the User.
   * @return an Optional object containing a UserDetails object if found
   * or an empty Optional object.
   */
  Optional<UserDetails> findByUsername(String username);

  /**
   * Is used to find a User object
   * by its username in the application database.
   *
   * @param username to be used to find the User.
   * @return an Optional object containing a User object if found
   * or an empty Optional object.
   */
  Optional<User> findUserByUsername(String username);
}
