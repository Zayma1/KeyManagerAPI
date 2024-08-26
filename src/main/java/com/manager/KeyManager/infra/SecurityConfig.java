package com.manager.KeyManager.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  internalFilter internalFilter;
  @Bean
  public SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/admin/registerNewUser").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/auth/verifyBiometric").permitAll()
                    .requestMatchers(HttpMethod.GET, "/auth/verifyLoginState").permitAll()
                    .requestMatchers(HttpMethod.POST, "/admin/createProtocol").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/dataManager/**").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/auth/verifyLogin").permitAll()
                    .requestMatchers(HttpMethod.GET, "/admin/dataManager/getAllProtocols").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/dataManager/getProtocolById").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/admin/dataManager/getUserById").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/admin/dataManager/deleteProtocol").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/admin/dataManager/deleteUser").hasAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/arduino/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/arduino/**").permitAll()
                    .anyRequest().permitAll())
            .addFilterBefore(this.internalFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }
}
