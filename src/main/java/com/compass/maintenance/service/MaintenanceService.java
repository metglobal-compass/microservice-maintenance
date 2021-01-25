package com.compass.maintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

  @Value("${maintenance.project}")
  private String projectName;

  private final StringRedisTemplate redisTemplate;

  @Autowired
  public MaintenanceService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setMaintenanceMode(Boolean maintenanceMode) {
    redisTemplate.opsForValue().set(projectName + ":maintenance", maintenanceMode.toString());
  }

  public boolean checkMaintenanceMode() {
    String isMaintenance = redisTemplate.opsForValue().get(projectName + ":maintenance");

    return Boolean.parseBoolean(isMaintenance); // If string isn't "true", it always returns false.
  }
}
