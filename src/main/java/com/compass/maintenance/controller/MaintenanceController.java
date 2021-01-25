package com.compass.maintenance.controller;

import com.compass.maintenance.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
  public String lockAction() {
    service.lock();

    return "on";
  }

  @PatchMapping("/unlock")
  public String unlockAction() {
    service.unlock();

    return "off";
  }
}
