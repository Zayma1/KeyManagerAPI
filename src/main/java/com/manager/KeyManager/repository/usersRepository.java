package com.manager.KeyManager.repository;

import com.manager.KeyManager.Entity.users;
import com.manager.KeyManager.roles.UserRoles;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface usersRepository extends JpaRepository<users,Integer> {
  UserDetails findByusername(String username);
  users findByBiometricsID(int id);

  List<users> findAllByrole(UserRoles role);
  @Modifying
  @Transactional
  @Query(value = "UPDATE users SET is_biometric_verified = ?1 WHERE biometricsid = ?2",nativeQuery = true)
  void verifyBiometric(boolean isVerified, int id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE users SET biometricsid = ?1, username = ?2 WHERE userid = ?3",nativeQuery = true)
  void updateUserData(int biometricID, String username, int userID);
}
