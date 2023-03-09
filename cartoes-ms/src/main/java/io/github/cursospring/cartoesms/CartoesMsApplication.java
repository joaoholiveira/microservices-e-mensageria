package io.github.cursospring.cartoesms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CartoesMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartoesMsApplication.class, args);
	}

}
