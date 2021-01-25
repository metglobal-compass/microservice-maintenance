package com.compass.maintenance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(500) // If main application has another security configuration, do not override it.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${maintenance.username}")
  private String maintenanceUser;

  @Value("{noop}${maintenance.password}")
  private String maintenancePassword;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/maintenance/**").hasRole("MAINTENANCE")
        .anyRequest().permitAll()
        .and()
        .httpBasic();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser(maintenanceUser)
        .password(maintenancePassword)
        .roles("MAINTENANCE");
  }
}
