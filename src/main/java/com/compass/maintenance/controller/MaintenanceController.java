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

  private static final String LOCK_MESSAGE = "Server is under maintenance.";
  private static final String UNLOCK_MESSAGE = "Server is online.";

  private final MaintenanceService service;

  @Autowired
  public MaintenanceController(MaintenanceService service) {
    this.service = service;
  }

  @PatchMapping("/lock")
  public MaintenanceResponse lockAction() {
    boolean success = true;
    String message = LOCK_MESSAGE;

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
    String message = UNLOCK_MESSAGE;

    try {
      service.setMaintenanceMode(false);
    } catch (Exception ex) {
      success = false;
      message = ex.getMessage();
    }

    return new MaintenanceResponse().setSuccess(success).setMessage(UNLOCK_MESSAGE);
  }
}
