package com.manager.KeyManager.roles;

public enum protocolStatus {
  AVAILABLE("DISPONIVEL"),
  GOT("CHAVE RETIRADA"),
  RETURN("DEVOLVER");

  private String status;

  protocolStatus(String status) {
    this.status = status;
  }

  public String getStatusName() {
    return status;
  }

  public static protocolStatus fromString(String status) {
    for (protocolStatus ps : protocolStatus.values()) {
      if (ps.getStatusName().equals(status)) {
        return ps;
      }
    }
    throw new IllegalArgumentException("No enum constant with status " + status);
  }

}
