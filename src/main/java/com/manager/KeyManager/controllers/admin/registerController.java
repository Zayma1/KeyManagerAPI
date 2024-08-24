package com.manager.KeyManager.controllers.admin;

import com.manager.KeyManager.Entity.dto.createProtocolDTO;
import com.manager.KeyManager.Entity.dto.registerUserDTO;
import com.manager.KeyManager.Entity.protocols;
import com.manager.KeyManager.Entity.users;
import com.manager.KeyManager.repository.protocolRepository;
import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.roles.UserRoles;
import com.manager.KeyManager.roles.protocolStatus;
import com.manager.KeyManager.services.TokenService;
import com.manager.KeyManager.services.arduinoActionService;
import com.manager.KeyManager.services.dateTimeConvertService;
import com.manager.KeyManager.services.loginSaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin")
public class registerController {

  @Autowired
  usersRepository usersRepository;

  @Autowired
  protocolRepository protocolRepository;

  @Autowired
  dateTimeConvertService dateTimeConvertService;

  @Autowired
  loginSaverService loginSaverService;

  @Autowired
  TokenService tokenService;

  @Autowired
  arduinoActionService arduinoActionService;

  @PostMapping("/activeRegisterMode")
  public ResponseEntity activeRegisterMode(){
    this.arduinoActionService.setAction(2);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/setRegisterBiometricStatus/{status}")
  public ResponseEntity setRegisterBiometricStatus(@PathVariable(value = "status")String status){
    var split = status.split(",");
    if(!split[1].equals("0")){
      this.arduinoActionService.setRegisterStatusCode(Integer.parseInt(split[1]));
      this.arduinoActionService.setCurrentRegisterStatus(split[0]);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getRegisterBiometricStatus")
  public ResponseEntity getRegisterBiometricStatus(){
    if(this.arduinoActionService.getRegisterStatusCode() == 2){
      return ResponseEntity.accepted().body(this.loginSaverService.getLastID());
    }else{
      return ResponseEntity.ok(this.arduinoActionService.getCurrentRegisterStatus());
    }
  }

  @PostMapping("/registerNewUser")
  public ResponseEntity registerUser(@RequestBody registerUserDTO userData){
    var checkUser = this.usersRepository.findByBiometricsID(userData.biometricID());

    if(checkUser == null){
      users newUser = new users(
              userData.username(),
              userData.biometricID(),
              UserRoles.values()[userData.role()]
      );
      this.usersRepository.save(newUser);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/createProtocol")
  public ResponseEntity createProtocol(@RequestBody createProtocolDTO protocolData){
    var getUser = this.usersRepository.findByBiometricsID(protocolData.userBiometric());
    if(getUser != null){
      LocalDate date = this.dateTimeConvertService.convertDate(protocolData.Returndate());
      LocalTime time = this.dateTimeConvertService.converTime(protocolData.Returntime());

      LocalDate initalDate = this.dateTimeConvertService.convertDate(protocolData.initialDate());
      LocalTime initalTime = this.dateTimeConvertService.converTime(protocolData.initialTime());

      LocalDateTime returnDate = LocalDateTime.of(date, time);
      LocalDateTime initDate = LocalDateTime.of(initalDate,initalTime);

      protocols newProtocol = new protocols(
              getUser,
              protocolStatus.AVAILABLE,
              protocolData.port(),
              initDate,
              returnDate
      );
      this.protocolRepository.save(newProtocol);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

}
