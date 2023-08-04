package com.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // Se usa para configurar los Hilos Trhead
public class MarketingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketingApplication.class, args);
		System.out.println("Prueba desde marketing");
	}

}
