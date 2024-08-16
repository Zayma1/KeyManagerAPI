package com.manager.KeyManager.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${tokenGenerate.secret}")
  private String secret;

  public String GenerateToken(UserDetails user){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
              .withIssuer("KeyManager")
              .withSubject(user.getUsername())
              .withExpiresAt(genExpirationDate())
              .sign(algorithm);
      return token;
    }catch(Exception erro){
      throw new RuntimeException("Error on generating token", erro);
    }
  }

  public String ValidateToken(String token){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
              .withIssuer("KeyManager")
              .build()
              .verify(token)
              .getSubject();
    }catch(Exception erro){
      throw new RuntimeException("Error on verifying token", erro);
    }
  }

  private Instant genExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
