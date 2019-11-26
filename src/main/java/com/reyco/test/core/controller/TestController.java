package com.reyco.test.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reyco.test.core.domain.UserEntity;
import com.reyco.test.core.service.TestService;

@RestController
public class TestController {

	@Autowired
	TestService testService;
	
	@RequestMapping("/test")
	public String test() {
		testService.query();
		return "ok";
	}
}
