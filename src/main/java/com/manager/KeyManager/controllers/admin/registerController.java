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
import com.manager.KeyManager.services.dateTimeConvertService;
import org.springframework.beans.factory.annotation.Autowired;
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
  TokenService tokenService;

  @PostMapping("/registerNewUser")
  public ResponseEntity registerUser(@RequestBody registerUserDTO userData){
    var checkUser = this.usersRepository.findByBiometricsID(userData.biometricID());

    if(checkUser == null){
      users newUser = new users(
              userData.username(),
              userData.biometricID(),
              UserRoles.values()[userData.role()],
              false
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
      LocalDate date = this.dateTimeConvertService.convertDate(protocolData.date());
      LocalTime time = this.dateTimeConvertService.converTime(protocolData.time());

      LocalDateTime localDate = LocalDateTime.of(date, time);

      protocols newProtocol = new protocols(
              getUser,
              protocolStatus.AVAILABLE,
              localDate
      );
      this.protocolRepository.save(newProtocol);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

}
