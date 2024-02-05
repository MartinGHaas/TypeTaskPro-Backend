package com.typetaskpro.core.repositories.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.typetaskpro.core.domain.image.model.ProfilePictureImage;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePictureImage, String> {
  
  @Transactional
  @Query(value = "SELECT * FROM profile_pictures WHERE owner = ?1", nativeQuery = true )
  public Optional<ProfilePictureImage> getProfilePictureByUserId(long userId);
}
