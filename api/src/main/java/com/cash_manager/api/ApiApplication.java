package com.cash_manager.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiApplication {

	/**
	* Entry point for the application. This method is called by Jaquibus to start the application
	* 
	* param args - command line arguments passed to
	*/
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}