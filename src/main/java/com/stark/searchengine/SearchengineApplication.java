package com.stark.searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
public class SearchengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchengineApplication.class, args);
	}
}
