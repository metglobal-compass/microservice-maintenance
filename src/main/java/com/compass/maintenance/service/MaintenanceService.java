package com.compass.maintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

  private final StringRedisTemplate redisTemplate;

  @Autowired
  public MaintenanceService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void lock() {
    redisTemplate.opsForValue().set("emrika", "true");
  }

  public void unlock() {
    redisTemplate.opsForValue().set("emrika", "false");
  }

  public boolean checkMaintenanceMode() {
    String isMaintenance = redisTemplate.opsForValue().get("emrika");

    return Boolean.parseBoolean(isMaintenance);
  }
}
