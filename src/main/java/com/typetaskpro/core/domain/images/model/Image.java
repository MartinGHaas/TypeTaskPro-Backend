package com.typetaskpro.core.domain.images.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "images")
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Image {
  
  @Id
  @GeneratedValue(
    strategy = GenerationType.UUID
  )
  private String id;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(
    name = "data",
    nullable = false
  )
  private byte[] data;

  public Image(byte[] data) {
    this.data = data;
  }
}
