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

  @ManyToOne
  private keys key;

  @Column(name = "protocolStatus")
  private protocolStatus status;

  @Column(name = "returnDate")
  private LocalDateTime returnDate;
}
