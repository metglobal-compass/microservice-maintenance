package com.compass.maintenance.model;

public class MaintenanceResponse {

  private boolean success;
  private String message;

  public boolean isSuccess() {
    return success;
  }

  public MaintenanceResponse setSuccess(boolean success) {
    this.success = success;

    return this;
  }

  public String getMessage() {
    return message;
  }

  public MaintenanceResponse setMessage(String message) {
    this.message = message;

    return this;
  }
}
