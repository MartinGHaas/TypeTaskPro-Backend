package com.typetaskpro.core.domain.image.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>Abstract Image</h1>
 * <p>Created to have the default image data in the application.</p>
 * <p>All images in the application should extend this class.</p>
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
abstract class AbstractImage {
  
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

  public AbstractImage(byte[] data) {
    this.data = data;
  }
}
