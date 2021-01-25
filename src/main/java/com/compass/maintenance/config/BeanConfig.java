package com.compass.maintenance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class BeanConfig {

  @Bean
  public LettuceConnectionFactory lettuceFactory() {
    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();

    return new LettuceConnectionFactory(redisConfig);
  }
}
