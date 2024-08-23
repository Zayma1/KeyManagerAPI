package com.manager.KeyManager.controllers;


import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.roles.UserRoles;
import com.manager.KeyManager.services.TokenService;
import com.manager.KeyManager.services.loginSaverService;
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

  @Autowired
  loginSaverService loginSaverService;

  @GetMapping("/verifyLogin")
  public ResponseEntity verifyLoginState() {

    var getUser = this.usersRepository.findByBiometricsID(this.loginSaverService.getLastID());
    if (getUser != null) {
      if (getUser.isBiometricVerified()) {
        Authentication auth = new UsernamePasswordAuthenticationToken(getUser.getUsername(), getUser.getPassword(), getUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        var token = this.tokenService.GenerateToken((UserDetails) getUser) + "," + getUser.getRole().ordinal();
        return ResponseEntity.ok(token);
      }else{
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.badRequest().build();
  }
}