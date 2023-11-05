package com.example.KanbanGateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@EnableEurekaClient


public class KanbanGateWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanGateWayApplication.class, args);
	}

}
