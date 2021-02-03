package com.compass.maintenance.filter;

import com.compass.maintenance.params.MaintenanceParams;
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
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceFilter implements Filter {

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
    String expireDateTime = service.getExpireDateTime();
    String path = ((HttpServletRequest) request).getServletPath();

    if (expireDateTime != null && !path.startsWith("/maintenance")) {
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;

      httpServletResponse.addHeader(HttpHeaders.RETRY_AFTER, expireDateTime);
      httpServletResponse.sendError(
          HttpServletResponse.SC_SERVICE_UNAVAILABLE,
          MaintenanceParams.LOCK_MESSAGE.getMessage()
      );
    } else {
      filterChain.doFilter(request, response);
    }

    if (path.startsWith("/maintenance")) {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }

  @Override
  public void destroy() {

  }
}
