package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SQLite_WebServer {

	public static void main(String[] args) {
		SpringApplication.run(SQLite_WebServer.class, args);
	}

}
