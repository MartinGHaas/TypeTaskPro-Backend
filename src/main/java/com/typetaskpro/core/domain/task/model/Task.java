package com.typetaskpro.core.domain.task.model;

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
  @EqualsAndHashCode.Exclude
  private final String id = UUID.randomUUID().toString();

  @Column(
    nullable = false
  )
  private String name;

  @Column
  private String description;
}
