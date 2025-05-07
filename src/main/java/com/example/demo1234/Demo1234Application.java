package com.example.demo1234;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.example.demo1234"})
@SpringBootApplication
public class Demo1234Application {

	public static void main(String[] args) {

		SpringApplication.run(Demo1234Application.class, args);
		//redeploy
	}

}
