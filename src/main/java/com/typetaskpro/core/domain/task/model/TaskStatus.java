package com.typetaskpro.core.domain.task.model;

public enum TaskStatus {
  TODO("todo"),
  IN_PROGRESS("in_progress"),
  IN_REVIEW("in_review"),
  COMPLETED("completed");

  public String status;

  TaskStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
