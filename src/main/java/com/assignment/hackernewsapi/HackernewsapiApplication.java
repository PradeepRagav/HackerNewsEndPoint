package com.assignment.hackernewsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@Configuration
@SpringBootApplication
public class HackernewsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackernewsapiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplateClient(){
		return new RestTemplate();
	}

	@Bean
	public ScheduledExecutorService scheduledExecutorService(){
		//TODO : Add configs
		return Executors.newScheduledThreadPool(2);
	}

	@Bean
	public ExecutorService executorService(){
		//TODO : Add configs
		int corePoolSize = 10;
		int maxPoolSize = 20;
		long keepAliveTime = 60 * 60;

		return  new ThreadPoolExecutor(
			corePoolSize,
			maxPoolSize,
			keepAliveTime,
			TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>()
		);
	}

}
