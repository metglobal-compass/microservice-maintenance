package com.compass.maintenance.filter;

import com.compass.maintenance.service.MaintenanceService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class MaintenanceFilter implements Filter {

  @Value("${maintenance.username}")
  private String username;

  @Value("${maintenance.password}")
  private String password;

  private final MaintenanceService service;

  @Autowired
  public MaintenanceFilter(MaintenanceService service) {
    this.service = service;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    boolean isMaintenance = service.checkMaintenanceMode();

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;

    String authKey = httpServletRequest.getHeader("Authorization");
    String[] credentials = this.getCredentialsFromBasicAuthHeader(authKey);

    String path = httpServletRequest.getServletPath();

    if (path.startsWith("/maintenance") && !this.authorize(credentials)) {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Wrong maintenance token");
    }

    if (isMaintenance && !path.equals("/maintenance/unlock")) {
      throw new RuntimeException("Server is under maintenance.");
    } else {
      filterChain.doFilter(request, response);
    }

  }

  @Override
  public void destroy() {

  }

  private String[] getCredentialsFromBasicAuthHeader(String rawHeader) {
    String lastWord = rawHeader.split(" ")[1];

    String decodedCredentials = new String(Base64.getDecoder().decode(lastWord));

    return decodedCredentials.split(":");
  }

  private boolean authorize(String[] credentials) {
    return username.equals(credentials[0]) && password.equals(credentials[1]);
  }
}
