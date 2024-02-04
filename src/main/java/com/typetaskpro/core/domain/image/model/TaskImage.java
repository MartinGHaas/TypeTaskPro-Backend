package com.typetaskpro.core.domain.image.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity(name = "tasks_image")
@Table(name = "tasks_image")
@NoArgsConstructor
public class TaskImage extends AbstractImage{

  public TaskImage(byte[] data) {
    super(data);
  }

  public TaskImage(String id, byte[] data) {
    super(id, data);
  }
}
