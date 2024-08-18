package com.manager.KeyManager.repository;

import com.manager.KeyManager.Entity.protocols;
import com.manager.KeyManager.Entity.users;
import com.manager.KeyManager.roles.protocolStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface protocolRepository extends JpaRepository<protocols, Integer> {
  protocols findByuser(users user);

  @Modifying
  @Transactional
  @Query(value = "UPDATE protocols SET protocol_status = ?1, user_userid = ?2, return_date = ?3 WHERE protocolid = ?4",nativeQuery = true)
  void updateProtocolData(
          int status, int userid, String localDateTime, int protocolID
  );

  @Modifying
  @Transactional
  @Query(value = "UPDATE protocols SET user_userid = ?1 WHERE protocolid = ?2",nativeQuery = true)
  void changeOwner(int userID, int protocolID);
}
