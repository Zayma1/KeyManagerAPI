package com.manager.KeyManager.services;

import org.springframework.stereotype.Service;

@Service
public class arduinoActionService {
  int action = 0;

  String currentRegisterStatus = "FEITO O CADASTRO";

  int registerStatusCode = 0;

  public String getCurrentRegisterStatus() {
    return currentRegisterStatus;
  }

  public void setCurrentRegisterStatus(String currentRegisterStatus) {
    this.currentRegisterStatus = currentRegisterStatus;
  }

  public int getRegisterStatusCode() {
    return registerStatusCode;
  }

  public void setRegisterStatusCode(int registerStatusCode) {
    this.registerStatusCode = registerStatusCode;
  }

  public int getAction() {
    return action;
  }

  public void setAction(int action) {
    this.action = action;
  }

}
