package com.typetaskpro.core.domain.image.model;

import com.typetaskpro.core.domain.device.model.Device;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity(name = "devices_image")
@Table(name = "devices_image")
@NoArgsConstructor
public class DeviceImage extends AbstractImage {

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JoinColumn(
    name = "device_id"
  )
  private Device device;

  public DeviceImage(byte[] data) {
    super(data);
  }

  public DeviceImage(String id, byte[] data) {
    super(id, data);
  }

  public DeviceImage(Device device, byte[] data) {
    super(data);
    this.device = device;
  }
}
