package com.manager.KeyManager.Entity;

import com.manager.KeyManager.roles.protocolStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class protocols {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int protocolID;

  @ManyToOne
  private users user;

  @Column(name = "protocolStatus")
  private protocolStatus status;

  @Column(name = "returnDate")
  private LocalDateTime returnDate;

  public protocols(){}

  public protocols(int protocolID, users user, protocolStatus status, LocalDateTime returnDate) {
    this.protocolID = protocolID;
    this.user = user;
    this.status = status;
    this.returnDate = returnDate;
  }

  public protocols(users user, protocolStatus status, LocalDateTime returnDate) {
    this.user = user;
    this.status = status;
    this.returnDate = returnDate;
  }

  public int getProtocolID() {
    return protocolID;
  }

  public void setProtocolID(int protocolID) {
    this.protocolID = protocolID;
  }

  public users getUser() {
    return user;
  }

  public void setUser(users user) {
    this.user = user;
  }

  public protocolStatus getStatus() {
    return status;
  }

  public void setStatus(protocolStatus status) {
    this.status = status;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }
}
