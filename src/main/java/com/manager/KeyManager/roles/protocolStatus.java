package com.manager.KeyManager.roles;

public enum protocolStatus {
  AVAILABLE("AVAILABLE"),
  GOT("GOT"),
  RETURN("RETURN");

  private String status;

  protocolStatus(String status) {
    this.status = status;
  }
}
