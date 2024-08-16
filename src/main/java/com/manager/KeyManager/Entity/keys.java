package com.manager.KeyManager.Entity;

import jakarta.persistence.*;

@Entity
public class keys {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int keyID;

  @Column(name = "keyname")
  String keyname;
}
