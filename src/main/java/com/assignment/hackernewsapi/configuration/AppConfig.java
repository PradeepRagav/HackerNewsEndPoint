package com.assignment.hackernewsapi.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.*;

@Configuration
public class AppConfig {

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

    @Bean
    public RestTemplate restTemplateClient(){
        return new RestTemplate();
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService(){
        //TODO : Add configs
        return Executors.newScheduledThreadPool(Integer.parseInt(environment.getProperty("scheduled.executor.service.pool.size")));
    }

    @Bean
    public ExecutorService executorService(){
        //TODO : Add configs
        int corePoolSize = Integer.parseInt(environment.getProperty("executor.service.core.pool.size"));
        int maxPoolSize = Integer.parseInt(environment.getProperty("executor.service.max.pool.size"));
        long keepAliveTime = Integer.parseInt(environment.getProperty("executor.service.keep.alive.time"));

        return  new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>()
        );
    }
}
