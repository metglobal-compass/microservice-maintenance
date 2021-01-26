package com.compass.maintenance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

  @Value("${maintenance.username}")
  private String maintenanceUser;

  @Value("{noop}${maintenance.password}")
  private String maintenancePassword;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    Object principal = auth.getPrincipal();

    return auth;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
