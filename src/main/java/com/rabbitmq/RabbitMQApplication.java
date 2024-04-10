package com.rabbitmq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RabbitMQApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(RabbitMQApplication.class)
				.web(WebApplicationType.SERVLET)
				.run(args);
	}
}