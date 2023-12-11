package com.exe.inventorymsystemserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryMSystemServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(InventoryMSystemServerApplication.class);
	public static void main(String[] args) {
		logger.info("Your log message");
		System.out.println("HELLO");
		SpringApplication.run(InventoryMSystemServerApplication.class, args);
	}

}
