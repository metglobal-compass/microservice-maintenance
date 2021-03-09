package com.compass.maintenance.controller;

import com.compass.maintenance.model.MaintenanceResponse;
import com.compass.maintenance.params.MaintenanceParams;
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
    boolean success = true;
    String message = MaintenanceParams.LOCK_MESSAGE.getMessage();

    try {
      service.setMaintenanceMode(true);
    } catch (Exception ex) {
      success = false;
      message = ex.getMessage();
    }

    return new MaintenanceResponse().setSuccess(success).setMessage(message);
  }

  @PatchMapping("/unlock")
  public MaintenanceResponse unlockAction() {
    boolean success = true;
    String message = MaintenanceParams.UNLOCK_MESSAGE.getMessage();

    try {
      service.setMaintenanceMode(false);
    } catch (Exception ex) {
      success = false;
      message = ex.getMessage();
    }

    return new MaintenanceResponse().setSuccess(success).setMessage(message);
  }
}
