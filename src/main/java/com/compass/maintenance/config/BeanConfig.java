package com.compass.maintenance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class BeanConfig {

  @Value("${maintenance.host:localhost}")
  private String host;

  @Value("${maintenance.port:6379}")
  private Integer port;

  @Bean
  public LettuceConnectionFactory lettuceFactory() {
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);

    return new LettuceConnectionFactory(redisConfig);
  }
}
