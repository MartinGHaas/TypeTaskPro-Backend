package com.typetaskpro.core.domain.task.model;

import com.typetaskpro.core.domain.image.model.TaskImage;

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

@Entity(name = "tasks")
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Task {
  
  @Id
  @GeneratedValue(
    strategy = GenerationType.UUID
  )
  @EqualsAndHashCode.Exclude
  private String id;

  @Column(
    nullable = false
  )
  private String name;

  @Column
  private String description;

  @Column
  private TaskStatus status;

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JoinColumn(
    name = "image_id"
  )
  private TaskImage image;

  public Task(String name, String description) {
    this.name = name;
    this.description = description;
    this.status = TaskStatus.TODO;
  }
}
