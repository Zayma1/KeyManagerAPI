package com.manager.KeyManager.Entity;

import com.manager.KeyManager.roles.UserRoles;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class users implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int userID;

  @Column(name = "username")
  String username;

  @Column(name = "biometricsID")
  int biometricsID;

  @Column(name = "UserRole")
  UserRoles role;

  public users() {}

  public users(int userID, String username, int biometricsID, UserRoles role) {
    this.userID = userID;
    this.username = username;
    this.biometricsID = biometricsID;
    this.role = role;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getBiometricsID() {
    return biometricsID;
  }

  public void setBiometricsID(int biometricsID) {
    this.biometricsID = biometricsID;
  }

  public UserRoles getRole() {
    return role;
  }

  public void setRole(UserRoles role) {
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.role == UserRoles.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"));
    else return List.of(new SimpleGrantedAuthority("EMPLOYEE"));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
