package com.typetaskpro.core.domain.user.model;

public enum UserRole {
  ADMIN("admin"),
  USER("user");

  public String userRole;

  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getRole() {
    return this.userRole;
  }
}
