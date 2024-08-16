package com.manager.KeyManager.repository;

import com.manager.KeyManager.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface usersRepository extends JpaRepository<users,Integer> {
  UserDetails findByusername(String username);
}
