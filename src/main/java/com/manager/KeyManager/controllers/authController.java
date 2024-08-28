package com.manager.KeyManager.controllers;


import com.manager.KeyManager.Entity.dto.returnUserDTO;
import com.manager.KeyManager.repository.protocolRepository;
import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.roles.UserRoles;
import com.manager.KeyManager.services.TokenService;
import com.manager.KeyManager.services.arduinoActionService;
import com.manager.KeyManager.services.loginSaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@EnableAsync
public class authController {
  @Autowired
  usersRepository usersRepository;

  @Autowired
  TokenService tokenService;

  @Autowired
  loginSaverService loginSaverService;

  @Autowired
  protocolRepository protocolRepository;

  @Autowired
  arduinoActionService arduinoActionService;
  public CompletableFuture<String> asyncEndpoint() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        this.arduinoActionService.setAction(1);
        Thread.sleep(10000);
        this.arduinoActionService.setAction(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "Operação Assíncrona Completa!";
    });
  }

  @GetMapping("/verifyLogin")
  public ResponseEntity verifyLoginState() {
    asyncEndpoint();
    return ResponseEntity.ok().build();
  }
  @GetMapping("/login")
  public ResponseEntity login(){
    if(this.loginSaverService.getLastID() != 0){
      var getUser = this.usersRepository.findByBiometricsID(this.loginSaverService.getLastID());
      if (getUser != null) {
        Authentication auth = new UsernamePasswordAuthenticationToken(getUser.getUsername(), getUser.getPassword(), getUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        var token = this.tokenService.GenerateToken((UserDetails) getUser);
        var getProtocol = this.protocolRepository.findByuser(getUser);

        if(getProtocol != null){
          returnUserDTO returnUser = new returnUserDTO(
                  getUser.getUserID(),
                  token,
                  getUser.getRole().ordinal(),
                  getUser.getUsername(),
                  getUser.getBiometricsID(),
                  getProtocol.getPort()
                  );
          this.loginSaverService.setLastID(0);
          this.arduinoActionService.setAction(0);
          return ResponseEntity.ok(returnUser);
        }

        returnUserDTO returnUser = new returnUserDTO(
                getUser.getUserID(),
                token,
                getUser.getRole().ordinal(),
                getUser.getUsername(),
                getUser.getBiometricsID(),
                "0"
        );
        this.loginSaverService.setLastID(0);
        this.arduinoActionService.setAction(0);
        return ResponseEntity.ok(returnUser);

      }else{
        return ResponseEntity.notFound().build();
      }
    }else{
      return ResponseEntity.noContent().build();
    }
  }
}