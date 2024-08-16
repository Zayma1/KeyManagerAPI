package com.manager.KeyManager.roles;

public enum UserRoles {
  ADMIN("ADMIN"),
  EMPLOYEE("EMPLOYEE");
  private String role;

  UserRoles(String role) {
    this.role = role;
  }
}
