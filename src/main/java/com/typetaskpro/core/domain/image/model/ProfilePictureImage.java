package com.typetaskpro.core.domain.image.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity(name = "profile_pictures")
@Table(name = "profile_pictures")
@NoArgsConstructor
public class ProfilePictureImage extends AbstractImage {

  public ProfilePictureImage(byte[] data) {
    super(data);
  }

  public ProfilePictureImage(String id, byte[] data) {
    super(id, data);
  }
}
