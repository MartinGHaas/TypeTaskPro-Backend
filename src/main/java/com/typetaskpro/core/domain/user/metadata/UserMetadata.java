package com.typetaskpro.core.domain.user.metadata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name= "user_metadata")
@Table(name= "user_metadata")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserMetadata {
  
  @Id
  @Column(
    insertable = false
  )
  private long id;
  
  @Column(
    name = "total_projects"
  )
  private int totalProjects;
  
  @Column(
    name = "projects_in_development"
  )
  private int projectsInDevelopment;

  @Column(
    name = "projects_completed"
  )
  private int projectsCompleted;

  @Column(
    name = "total_tasks"
  )
  private int totalTasks;
  
  @Column(
    name = "tasks_in_progress"
  )
  private int tasksInProgress;

  @Column(
    name = "tasks_todo"
  )
  private int tasksToDo;

  @Column(
    name = "tasks_completed"
  )
  private int tasksCompleted;
  
  @Column(
    name = "last_touched_project_id"
  )
  private int lastTouchedProjectId;

  @Column(
    name = "last_touched_task_id"
  )
  private String lastTouchedTaskId;

  public UserMetadata(long id) {
    this.id = id;
  }
}
