package io.github.cursospring.avaliadorcreditoms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AvaliadorCreditoMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvaliadorCreditoMsApplication.class, args);
	}

}
