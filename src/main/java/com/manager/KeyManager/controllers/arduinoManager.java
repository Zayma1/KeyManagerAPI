package com.manager.KeyManager.controllers;

import com.manager.KeyManager.repository.protocolRepository;
import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.roles.UserRoles;
import com.manager.KeyManager.services.arduinoActionService;
import com.manager.KeyManager.services.loginSaverService;
import com.manager.KeyManager.services.portService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arduino")
public class arduinoManager {
  @Autowired
  arduinoActionService arduinoActionService;

  @Autowired
  protocolRepository protocolRepository;

  @Autowired
  portService portService;

  @Autowired
  usersRepository usersRepository;

  @Autowired
  loginSaverService loginSaverService;

  @GetMapping("/getAction")
  public ResponseEntity getAction(){
    System.out.println(this.portService.getPort1());
    return ResponseEntity.ok(this.arduinoActionService.getAction());
  }

  @PostMapping("/setAction/{action}")
  public ResponseEntity setAction(@PathVariable(value = "action")String action){
    this.arduinoActionService.setAction(Integer.parseInt(action));
    return ResponseEntity.ok().build();
  }

  @PostMapping("/setLastID/{id}")
  public ResponseEntity setLastID(@PathVariable(value = "id")String id){
    this.loginSaverService.setLastID(Integer.parseInt(id));
    return ResponseEntity.ok().build();
  }

  @PostMapping("/setLastLoginID/{id}")
  public ResponseEntity setLastLoginID(@PathVariable(value = "id")String id){
    var getUser = this.usersRepository.findByBiometricsID(Integer.parseInt(id));

    if(getUser != null){
      var protocol = this.protocolRepository.findByuser(getUser);
      if(getUser.getRole() == UserRoles.EMPLOYEE && protocol != null){
        String arduinoCommand = "EMPLOYEE-" + protocol.getPort();
        this.arduinoActionService.setAction(0);
        this.loginSaverService.setLastID(Integer.parseInt(id));
        return ResponseEntity.ok(arduinoCommand);
      }
      this.loginSaverService.setLastID(Integer.parseInt(id));
      this.arduinoActionService.setAction(0);
      return ResponseEntity.notFound().build();
    }else{
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/updatePortStatus/{ports}")
  public ResponseEntity updatePortStatus(@PathVariable(value = "ports")String ports){
    var split = ports.split(",");
    this.portService.setPort1(Integer.parseInt(split[0]));
    this.portService.setPort2(Integer.parseInt(split[1]));
    //this.portService.setPort3(Integer.parseInt(split[2]));
    //this.portService.setPort4(Integer.parseInt(split[3]));
    return ResponseEntity.ok().build();
  }
}
