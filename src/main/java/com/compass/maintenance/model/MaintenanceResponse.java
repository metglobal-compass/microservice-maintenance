package com.compass.maintenance.model;

public class MaintenanceResponse {

  private boolean isMaintenance;

  public boolean isMaintenance() {
    return isMaintenance;
  }

  public MaintenanceResponse setMaintenance(boolean maintenance) {
    this.isMaintenance = maintenance;

    return this;
  }
}
