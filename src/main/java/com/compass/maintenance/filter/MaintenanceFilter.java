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
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class MaintenanceFilter implements Filter {

  private static final String LOCK_MESSAGE = "Server is under maintenance.";

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
    String path = ((HttpServletRequest) request).getServletPath();

    if (isMaintenance && !path.startsWith("/maintenance")) {
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;

      httpServletResponse.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, LOCK_MESSAGE);
    } else {
      filterChain.doFilter(request, response);
    }

    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Override
  public void destroy() {

  }
}
