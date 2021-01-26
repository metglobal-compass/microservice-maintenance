package com.compass.maintenance.filter;

import com.compass.maintenance.service.MaintenanceService;
import java.io.IOException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class MaintenanceFilter implements Filter {

  @Value("${maintenance.token}")
  private String token;

  private final String HEADER_KEY = "X-Maintenance-Token";

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

    String headerToken = httpServletRequest.getHeader(HEADER_KEY);

    String path = httpServletRequest.getServletPath();

    if (path.startsWith("/maintenance") && !headerToken.equals(token)) {
      throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Wrong maintenance token");
    }

    if (isMaintenance && !path.equals("/maintenance/unlock")) {
      throw new RuntimeException("Server is in maintenance mode. Please try again.");
    } else {
      filterChain.doFilter(request, response);
    }

    //SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Override
  public void destroy() {

  }
}
