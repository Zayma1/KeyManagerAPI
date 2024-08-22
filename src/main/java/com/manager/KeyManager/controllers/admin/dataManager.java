package com.manager.KeyManager.controllers.admin;

import com.manager.KeyManager.Entity.dto.createProtocolDTO;
import com.manager.KeyManager.Entity.dto.registerUserDTO;
import com.manager.KeyManager.Entity.dto.returnProtocolsDTO;
import com.manager.KeyManager.repository.protocolRepository;
import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.roles.UserRoles;
import com.manager.KeyManager.roles.protocolStatus;
import com.manager.KeyManager.services.dateTimeConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/dataManager")
public class dataManager {
  @Autowired
  dateTimeConvertService dateTimeConvertService;
  @Autowired
  usersRepository usersRepository;

  @Autowired
  protocolRepository protocolRepository;
  @GetMapping("/getAllUsers")
  public ResponseEntity getAllUsers(){
    var allUsers = this.usersRepository.findAllByrole(UserRoles.EMPLOYEE);
    List<registerUserDTO> filter = new ArrayList<>();
    allUsers.forEach(u ->{
      registerUserDTO add = new registerUserDTO(
              u.getUsername(),
              u.getBiometricsID(),
              1,
              u.getUserID()
      );
      filter.add(add);
    });
    return ResponseEntity.ok(filter);
  }

  @GetMapping("/getAllProtocols")
  public ResponseEntity getAllProtocols(){
    var allProtocols = this.protocolRepository.findAll();
    List<returnProtocolsDTO> protocols = new ArrayList<>();

    allProtocols.forEach(p ->{
      registerUserDTO filterUser = new registerUserDTO(
              p.getUser().getUsername(),
              p.getUser().getBiometricsID(),
              1,
              p.getUser().getUserID()
      );

      returnProtocolsDTO addProtocol = new returnProtocolsDTO(
              p.getProtocolID(),
              filterUser,
              p.getStatus().getStatusName(),
              p.getReturnDate().toLocalDate().toString(),
              p.getReturnDate().toLocalTime().toString(),
              p.getInitialDate().toLocalDate().toString(),
              p.getInitialDate().toLocalTime().toString()
      );
      protocols.add(addProtocol);
    });

    return ResponseEntity.ok(protocols);
  }

  @PostMapping("/updateProtocolData")
  public ResponseEntity updateProtocolData(@RequestBody createProtocolDTO newProtocolData){
    protocolStatus getStatus = protocolStatus.fromString(newProtocolData.status());
    LocalTime time = this.dateTimeConvertService.converTime(newProtocolData.Returntime());
    LocalDate date = this.dateTimeConvertService.convertDate(newProtocolData.Returndate());
    LocalTime initTime = this.dateTimeConvertService.converTime(newProtocolData.initialTime());
    LocalDate initDate = this.dateTimeConvertService.convertDate(newProtocolData.initialDate());
    LocalDateTime returnDate = LocalDateTime.of(date,time);
    LocalDateTime initialDate = LocalDateTime.of(initDate,initTime);

    this.protocolRepository.updateProtocolData(
          getStatus.ordinal(),
          newProtocolData.userBiometric(),
          returnDate.toString(),
          initialDate.toString(),
          newProtocolData.protocolID()
    );
    return ResponseEntity.ok().build();
  }

  @PostMapping("/updateUserData")
  public ResponseEntity updateUserData(@RequestBody registerUserDTO newUserData){
    this.usersRepository.updateUserData(
            newUserData.biometricID(),
            newUserData.username(),
            newUserData.userID()
    );
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/changeProtocolOwner/{protocolID}/{userid}")
  public ResponseEntity changeProtocolOwner(@PathVariable(value = "protocolID")String protocolID,@PathVariable(value = "userid")String userid){
    this.protocolRepository.changeOwner(Integer.parseInt(userid), Integer.parseInt(protocolID));
    return ResponseEntity.ok().build();
  }
}
