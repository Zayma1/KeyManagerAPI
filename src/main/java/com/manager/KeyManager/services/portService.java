package com.manager.KeyManager.services;

import com.manager.KeyManager.Entity.dto.portStatusDTO;
import com.manager.KeyManager.roles.protocolStatus;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class portService {
  int port1 = 1;
  int port2 = 1;
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

  public List<portStatusDTO> getPortsStatus(String ports){
      List<portStatusDTO> portsStatus = new ArrayList<>();

      for(int i = 0; i <= ports.length()-1;i++){
        char character = ports.charAt(i);
        if(Character.isDigit(character)){
          if(character == '1'){
            portStatusDTO portStatus = new portStatusDTO(
                    "PORTA 1",
                    protocolStatus.values()[port1]
            );
            portsStatus.add(portStatus);
          }

          if(character == '2'){
            portStatusDTO portStatus = new portStatusDTO(
                    "PORTA 2",
                    protocolStatus.values()[port2]
            );
            portsStatus.add(portStatus);
          }

          if(character == '3'){
            portStatusDTO portStatus = new portStatusDTO(
                    "PORTA 3",
                    protocolStatus.values()[port3]
            );
            portsStatus.add(portStatus);
          }

          if(character == '4'){
            portStatusDTO portStatus = new portStatusDTO(
                    "PORTA 4",
                    protocolStatus.values()[port4]
            );
            portsStatus.add(portStatus);
          }
        }
      }

      return portsStatus;
  }
}
