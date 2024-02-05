package com.typetaskpro.core.domain.image.model;

import com.typetaskpro.core.domain.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "profile_pictures")
@Table(name = "profile_pictures")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePictureImage extends AbstractImage {

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JoinColumn(
    name = "owner"
  )
  private User owner;

  public ProfilePictureImage(byte[] data) {
    super(data);
  }

  public ProfilePictureImage(String id, byte[] data) {
    super(id, data);
  }

  public ProfilePictureImage(User owner, byte[] data) {
    super(data);
    this.owner = owner;
  }
}
