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

  protocols findByprotocolID(int id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE protocols SET protocol_status = ?1, port = ?2, user_userid = ?3, return_date = ?4, initial_date = ?5 WHERE protocolid = ?6",nativeQuery = true)
  void updateProtocolData(
          int status, int port, int userid, String localDateTime, String initialDate, int protocolID
  );

  @Modifying
  @Transactional
  @Query(value = "UPDATE protocols SET user_userid = ?1 WHERE protocolid = ?2",nativeQuery = true)
  void changeOwner(int userID, int protocolID);
}
