package com.manager.KeyManager.controllers.admin;

import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class adminController {

  @Autowired
  usersRepository usersRepository;

  @Autowired
  TokenService tokenService;

}
