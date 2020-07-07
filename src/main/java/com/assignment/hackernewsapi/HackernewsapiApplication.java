package com.assignment.hackernewsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

@SpringBootApplication
public class HackernewsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackernewsapiApplication.class, args);
	}

}
