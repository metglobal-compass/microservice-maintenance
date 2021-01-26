package com.compass.maintenance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MaintenanceException extends RuntimeException {

  public MaintenanceException() {
    super("Wrong maintenance token.");
  }
}
