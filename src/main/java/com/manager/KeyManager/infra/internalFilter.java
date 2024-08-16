package com.manager.KeyManager.infra;

import com.manager.KeyManager.repository.usersRepository;
import com.manager.KeyManager.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class internalFilter extends OncePerRequestFilter {

  @Autowired
  TokenService tokenService;

  @Autowired
  usersRepository usersRepository;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      var getHeader = request.getHeader("Authorization");
      if(getHeader != null){
        String token = getHeader.replace("Bearer ", "");

        String username = this.tokenService.ValidateToken(token);
        var getUser = this.usersRepository.findByusername(username);

        if(getUser != null){
          Authentication authenticate = new UsernamePasswordAuthenticationToken(getUser.getUsername(),getUser.getPassword(),getUser.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authenticate);
        }

      }
      filterChain.doFilter(request,response);
  }
}
