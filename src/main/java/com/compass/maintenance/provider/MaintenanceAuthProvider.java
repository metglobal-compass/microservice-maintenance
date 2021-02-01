package com.compass.maintenance.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceAuthProvider implements AuthenticationProvider {

  @Value("${maintenance.username}")
  private String username;

  @Value("${maintenance.password}")
  private String password;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String authUser = auth.getName();
    String authPassword = auth.getCredentials().toString();

    if (authUser.equals(username) && authPassword.equals(password)) {
      return auth;
    }

    throw new BadCredentialsException("Username or password is wrong.");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
