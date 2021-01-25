package com.compass.maintenance.model;

public class MaintenanceResponse {

  private boolean success;
  private boolean isMaintenance;

  public boolean isSuccess() {
    return success;
  }

  public MaintenanceResponse setSuccess(boolean success) {
    this.success = success;

    return this;
  }

  public boolean isMaintenance() {
    return isMaintenance;
  }

  public MaintenanceResponse setMaintenance(boolean maintenance) {
    this.isMaintenance = maintenance;

    return this;
  }
}
