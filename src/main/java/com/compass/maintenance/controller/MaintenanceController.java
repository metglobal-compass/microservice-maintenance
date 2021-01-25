package com.compass.maintenance.controller;

import com.compass.maintenance.model.MaintenanceResponse;
import com.compass.maintenance.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

  private final MaintenanceService service;

  @Autowired
  public MaintenanceController(MaintenanceService service) {
    this.service = service;
  }

  @PatchMapping("/lock")
  public MaintenanceResponse lockAction() {
    service.setMaintenanceMode(true);

    return new MaintenanceResponse().setSuccess(true).setMaintenance(true);
  }

  @PatchMapping("/unlock")
  public MaintenanceResponse unlockAction() {
    service.setMaintenanceMode(false);

    return new MaintenanceResponse().setSuccess(true).setMaintenance(false);
  }
}
