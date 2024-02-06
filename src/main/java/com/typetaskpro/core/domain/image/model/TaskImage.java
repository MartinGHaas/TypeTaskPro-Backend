package com.typetaskpro.core.domain.image.model;

import com.typetaskpro.core.domain.task.model.Task;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h1>Task Image Entity</h1>
 * <p>This class represents a task image entity and table in the application.</p>
 */
@Entity(name = "tasks_image")
@Table(name = "tasks_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskImage extends AbstractImage{

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JoinColumn(
    name = "task_id"
  )
  private Task task;

  public TaskImage(byte[] data) {
    super(data);
  }

  public TaskImage(String id, byte[] data) {
    super(id, data);
  }

  public TaskImage(Task task, byte[] data) {
    super(data);
    this.task = task;
  }
}
