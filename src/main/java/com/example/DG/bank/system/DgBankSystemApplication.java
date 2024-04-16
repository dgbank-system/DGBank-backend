package com.example.DG.bank.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
public class DgBankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgBankSystemApplication.class, args);
	}

}
