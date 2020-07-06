package com.assignment.hackernewsapi.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        String redisHost = environment.getProperty("redis.host");
        String redisPort = environment.getProperty("redis.port");
        String minIdleConnections = environment.getProperty("redis.min.idle.connections");
        String maxConnections = environment.getProperty("redis.max.connections");
        Config config = new Config();
        config
            .useSingleServer()
            .setAddress(
                "redis://" + redisHost + ":"
                    + redisPort)
            .setConnectionMinimumIdleSize(
                Integer.parseInt(minIdleConnections))
            .setConnectionPoolSize(Integer.parseInt(maxConnections));
        return Redisson.create(config);
    }
}
