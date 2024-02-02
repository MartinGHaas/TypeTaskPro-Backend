package com.typetaskpro.core.domain.device.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  public Device(String name) {
    this.name = name;
  }
}
