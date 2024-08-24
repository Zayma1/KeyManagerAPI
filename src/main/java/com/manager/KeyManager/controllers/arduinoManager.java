package com.manager.KeyManager.controllers;

import com.manager.KeyManager.services.arduinoActionService;
import com.manager.KeyManager.services.loginSaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arduino")
public class arduinoManager {
  @Autowired
  arduinoActionService arduinoActionService;

  @Autowired
  loginSaverService loginSaverService;

  @GetMapping("/getAction")
  public ResponseEntity getAction(){
    return ResponseEntity.ok(this.arduinoActionService.getAction());
  }

  @PostMapping("/setLastID/{id}")
  public ResponseEntity setLastID(@PathVariable(value = "id")String id){
    this.loginSaverService.setLastID(Integer.parseInt(id));
    return ResponseEntity.ok().build();
  }
}
