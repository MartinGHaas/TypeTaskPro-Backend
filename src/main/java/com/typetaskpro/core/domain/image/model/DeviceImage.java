package com.typetaskpro.core.domain.image.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity(name = "devices_image")
@Table(name = "devices_image")
@NoArgsConstructor
public class DeviceImage extends AbstractImage {

  public DeviceImage(byte[] data) {
    super(data);
  }

  public DeviceImage(String id, byte[] data) {
    super(id, data);
  }
}
