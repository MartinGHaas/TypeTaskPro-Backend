package com.typetaskpro.core.domain.device.model;

import com.typetaskpro.core.domain.image.model.DeviceImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "devices")
@Table(name = "devices")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Device {
  
  @Id
  @GeneratedValue(
    strategy = GenerationType.UUID
  )
  @EqualsAndHashCode.Exclude
  private String id;

  @Column(
    nullable = false,
    unique = true,
    length = 15
  )
  private String name;

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JoinColumn(
    name = "image_id"
  )
  private DeviceImage image;

  public Device(String name) {
    this.name = name;
  }
}
