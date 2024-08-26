package com.manager.KeyManager.services;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;

@Service
public class portService {
  int port1 = 0;
  int port2 = 0;
  int port3 = 0;
  int port4 = 0;

  public int getPort1() {
    return port1;
  }

  public void setPort1(int port1) {
    this.port1 = port1;
  }

  public int getPort2() {
    return port2;
  }

  public void setPort2(int port2) {
    this.port2 = port2;
  }

  public int getPort3() {
    return port3;
  }

  public void setPort3(int port3) {
    this.port3 = port3;
  }

  public int getPort4() {
    return port4;
  }

  public void setPort4(int port4) {
    this.port4 = port4;
  }
}
