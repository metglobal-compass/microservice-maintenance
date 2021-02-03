package com.compass.maintenance.service;

import com.compass.maintenance.exception.MaintenanceException;
import com.compass.maintenance.params.MaintenanceParams;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

  private static final String KEY_TO_STORE = ":maintenance.expires";
  private static final String RFC_7231_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

  @Value("${maintenance.project}")
  private String projectName;

  @Value("${maintenance.ttl:3600}")
  private Integer ttl;

  private final StringRedisTemplate redisTemplate;

  @Autowired
  public MaintenanceService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setMaintenanceMode(boolean maintenanceMode) {
    boolean isMaintenance = this.getExpireDateTime() != null;

    if (maintenanceMode == isMaintenance) {
      throw new MaintenanceException(
          maintenanceMode
              ? MaintenanceParams.SERVER_ALREADY_LOCKED_MESSAGE.getMessage()
              : MaintenanceParams.SERVER_ALREADY_ONLINE_MESSAGE.getMessage()
      );
    }

    if (maintenanceMode) {
      long expireSeconds = ZonedDateTime.now(ZoneId.of("GMT")).toEpochSecond() + ttl;

      Instant instant = Instant.ofEpochSecond(expireSeconds + ttl);
      String expireDateTimeGMT = ZonedDateTime
          .ofInstant(instant, ZoneId.of("GMT"))
          .format(DateTimeFormatter.ofPattern(RFC_7231_DATE_FORMAT));

      // TODO: Try opsForHash.
      redisTemplate.opsForValue().set(projectName + KEY_TO_STORE, expireDateTimeGMT);

      redisTemplate.expire(projectName + KEY_TO_STORE, ttl, TimeUnit.SECONDS);
    } else {
      redisTemplate.delete(projectName + KEY_TO_STORE);
    }
  }

  public String getExpireDateTime() {
    return redisTemplate.opsForValue().get(projectName + KEY_TO_STORE);
  }
}
