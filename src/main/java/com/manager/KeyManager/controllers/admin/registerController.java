package com.manager.KeyManager.controllers.admin;

import com.manager.KeyManager.Entity.dto.createProtocolDTO;
import com.manager.KeyManager.Entity.dto.registerUserDTO;
import com.manager.KeyManager.Entity.protocols;
import com.manager.KeyManager.Entity.users;
import com.manager.KeyManager.repository.protocolRepository;
import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.roles.UserRoles;
import com.manager.KeyManager.roles.protocolStatus;
import com.manager.KeyManager.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
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

  @Autowired
  portService portService;

  @GetMapping("/loginVerify")
  public String loginVerify(ModelMap model){
    if(this.loginSaverService.getLastID() != 0){
      this.loginSaverService.setLastID(0);
      this.arduinoActionService.setAction(0);
      return "main";
    }else{
      model.addAttribute("status", "Coloque o dedo no leitor...");
      return "auth";
    }
  }

  @GetMapping("/main")
  public String main(ModelMap model){
    if(this.portService.getPort1() == 1) model.addAttribute("status1", "CHAVE DISPONÍVEL");
    else model.addAttribute("status1", "CHAVE RETIRADA");

    if(this.portService.getPort2() == 1) model.addAttribute("status2", "CHAVE DISPONÍVEL");
    else model.addAttribute("status1", "CHAVE RETIRADA");

    if(this.portService.getPort3() == 1) model.addAttribute("status3", "CHAVE DISPONÍVEL");
    else model.addAttribute("status3", "CHAVE RETIRADA");

    if(this.portService.getPort4() == 1) model.addAttribute("status4", "CHAVE DISPONÍVEL");
    else model.addAttribute("status4", "CHAVE RETIRADA");

    return "main";
  }

  @GetMapping("/RegisterPage")
  public String registerPage(ModelMap model){
    this.arduinoActionService.setAction(2);
    if(this.arduinoActionService.getCurrentRegisterStatus().equals("CADASTRO-DA-DIGITAL-CONCLUIDO")){
      return "main";
    }
    model.addAttribute("status", this.arduinoActionService.getCurrentRegisterStatus());
    return "register";
  }
  @PostMapping("/activeRegisterMode")
  public ResponseEntity activeRegisterMode(){
    this.arduinoActionService.setAction(2);
    this.arduinoActionService.setCurrentRegisterStatus("");
    this.arduinoActionService.setRegisterStatusCode(0);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/setRegisterBiometricStatus/{status}")
  public ResponseEntity setRegisterBiometricStatus(@PathVariable(value = "status")String status){
    var split = status.split(",");
    if(split[1].equals("10")){
      this.arduinoActionService.setCurrentRegisterStatus("Ocorreu um problema ao cadastrar a digital, por favor tente novamente");
      this.arduinoActionService.setRegisterStatusCode(10);
      this.loginSaverService.setLastID(0);
      this.arduinoActionService.setAction(0);
    }

    if(!split[1].equals("0")){
      if(split[0].equals("CADASTRO-DA-DIGITAL-CONCLUIDO")){
        this.arduinoActionService.setRegisterStatusCode(Integer.parseInt(split[1]));
        this.arduinoActionService.setCurrentRegisterStatus(split[0]);
        this.arduinoActionService.setAction(0);
        this.loginSaverService.setLastID(Integer.parseInt(split[1]));
        return ResponseEntity.ok().build();
      }
      this.arduinoActionService.setRegisterStatusCode(Integer.parseInt(split[1]));
      this.arduinoActionService.setCurrentRegisterStatus(split[0]);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.internalServerError().build();
  }

  @GetMapping("/getRegisterBiometricStatus")
  public ResponseEntity getRegisterBiometricStatus(){
    return ResponseEntity.ok(this.arduinoActionService.getCurrentRegisterStatus() + "," + this.arduinoActionService.getRegisterStatusCode());
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
      this.loginSaverService.setLastID(0);
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
