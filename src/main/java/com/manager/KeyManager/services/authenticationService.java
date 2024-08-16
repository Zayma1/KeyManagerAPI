package com.manager.KeyManager.services;

import com.manager.KeyManager.repository.usersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class authenticationService implements UserDetailsService {

  @Autowired
  usersRepository usersRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      var getUser = this.usersRepository.findByusername(username);
      if(getUser != null){
        return getUser;
      }
      return null;
  }
}
