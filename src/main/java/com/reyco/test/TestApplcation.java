package com.reyco.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reyco.aop.core.annotation.EnableAop;

@EnableAop
@SpringBootApplication
public class TestApplcation {
	
	public static void main(String[] args) {
		SpringApplication.run(TestApplcation.class, args);
	}
	
}
