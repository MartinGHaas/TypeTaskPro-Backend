package com.typetaskpro.core.domain.task.model;

import java.sql.Timestamp;

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

  @Column(name = "limit_date")
  private Timestamp limitDate;

  public Task(String name, String description, long limitDateDT) {
    this.name = name;
    this.description = description;
    this.status = TaskStatus.TODO;
    this.limitDate = new Timestamp(limitDateDT);
  }
}
