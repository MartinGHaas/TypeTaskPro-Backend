package com.typetaskpro.domain.device.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @EqualsAndHashCode.Exclude
  private final String id = UUID.randomUUID().toString();

  @Column(
    nullable = false,
    unique = true,
    length = 15
  )
  private String name;
}
