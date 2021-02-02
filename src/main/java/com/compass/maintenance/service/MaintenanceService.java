package com.compass.maintenance.service;

import com.compass.maintenance.exception.MaintenanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

  private static final String SERVER_ALREADY_LOCKED_MESSAGE = "Server is already locked.";
  private static final String SERVER_ALREADY_ONLINE_MESSAGE = "Server is already online.";

  @Value("${maintenance.project}")
  private String projectName;

  private final StringRedisTemplate redisTemplate;

  @Autowired
  public MaintenanceService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setMaintenanceMode(boolean maintenanceMode) {
    boolean isMaintenance = this.checkMaintenanceMode();

    if (maintenanceMode == isMaintenance) {
      throw new MaintenanceException(
          maintenanceMode ? SERVER_ALREADY_LOCKED_MESSAGE : SERVER_ALREADY_ONLINE_MESSAGE
      );
    }

    redisTemplate.opsForValue().set(projectName + ":maintenance", Boolean.toString(maintenanceMode));
  }

  public boolean checkMaintenanceMode() {
    String isMaintenance = redisTemplate.opsForValue().get(projectName + ":maintenance");

    return Boolean.parseBoolean(isMaintenance); // If string isn't "true", it always returns false.
  }
}
