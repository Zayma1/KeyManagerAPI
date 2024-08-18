package com.manager.KeyManager.controllers;


import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authController {
  @Autowired
  usersRepository usersRepository;

  @Autowired
  TokenService tokenService;

  @PostMapping("/verifyBiometric/{biometricID}")
  public ResponseEntity verifyBiometric(@PathVariable(value = "biometricID") String id) {
    var intID = Integer.parseInt(id);
    var getUser = this.usersRepository.findByBiometricsID(intID);
    if (getUser != null) {
      this.usersRepository.verifyBiometric(true, intID);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("/verifyLoginState/{biometricID}")
  public ResponseEntity verifyLoginState(@PathVariable(value = "biometricID") String id) {
    var intID = Integer.parseInt(id);

    var getUser = this.usersRepository.findByBiometricsID(intID);
    if (getUser != null) {
      if (getUser.isBiometricVerified()) {
        Authentication auth = new UsernamePasswordAuthenticationToken(getUser.getUsername(), getUser.getPassword(), getUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        var token = this.tokenService.GenerateToken((UserDetails) getUser);
        return ResponseEntity.ok(token);
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.badRequest().build();
  }
}