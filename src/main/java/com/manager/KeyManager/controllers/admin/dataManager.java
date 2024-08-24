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
              p.getPort(),
              p.getReturnDate().toLocalDate().toString(),
              p.getReturnDate().toLocalTime().toString(),
              p.getInitialDate().toLocalDate().toString(),
              p.getInitialDate().toLocalTime().toString()
      );
      protocols.add(addProtocol);
    });

    return ResponseEntity.ok(protocols);
  }

  @GetMapping("/getUserById/{userID}")
  public ResponseEntity getUserByID(@PathVariable(value = "userID")String id){
    var getUser = this.usersRepository.findByuserID(Integer.parseInt(id));
    if(getUser != null){
      registerUserDTO returnUser = new registerUserDTO(
              getUser.getUsername(),
              getUser.getBiometricsID(),
              getUser.getRole().ordinal(),
              getUser.getUserID()
      );
      return ResponseEntity.ok(returnUser);
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/getProtocolById/{protocolID}")
  public ResponseEntity getProtocolByID(@PathVariable(value = "protocolID")String id){
    var getProtocol = this.protocolRepository.findByprotocolID(Integer.parseInt(id));
    if(getProtocol != null){
      return ResponseEntity.ok(getProtocol);
    }
    return ResponseEntity.badRequest().build();
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
          newProtocolData.port(),
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
    var getUser = this.usersRepository.findByuserID(Integer.parseInt(userid));
    var getProtocol = this.protocolRepository.findByprotocolID(Integer.parseInt(protocolID));
    if(getUser != null && getProtocol != null){
      this.protocolRepository.changeOwner(Integer.parseInt(userid), Integer.parseInt(protocolID));
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @DeleteMapping("/deleteUser/{userID}")
  public ResponseEntity deleteUser(@PathVariable(value = "userID")String id){
    this.usersRepository.deleteById(Integer.parseInt(id));
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/deleteProtocol/{protocolID}")
  public ResponseEntity deleteProtocol(@PathVariable(value = "protocolID")String id){
    this.protocolRepository.deleteById(Integer.parseInt(id));
    return ResponseEntity.ok().build();
  }
}
