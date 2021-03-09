package com.compass.maintenance.exception;

/**
* Exception will be only handled at service layer.
 */
public class MaintenanceException extends RuntimeException {

  public MaintenanceException(String message) {
    super(message);
  }
}
