package com.compass.maintenance.params;

public enum MaintenanceParams {
  LOCK_MESSAGE("Server is under maintenance."),
  UNLOCK_MESSAGE("Server is online."),
  SERVER_ALREADY_LOCKED_MESSAGE("Server is already locked."),
  SERVER_ALREADY_ONLINE_MESSAGE("Server is already online.");

  private final String message;

  MaintenanceParams(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
