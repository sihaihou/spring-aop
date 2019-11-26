package com.reyco.test.core.aop;

import org.springframework.stereotype.Component;

import com.reyco.aop.core.annotation.MyAfter;
import com.reyco.aop.core.annotation.MyAround;
import com.reyco.aop.core.annotation.MyAspect;
import com.reyco.aop.core.annotation.MyBefore;

@Component
@MyAspect
public class TestAop {

	@MyBefore("com.reyco.test.core.service")
	public void testBefore() throws Throwable {
		System.out.println("before   -----------------");
	}

	@MyAfter("com.reyco.test.core.service")
	public void testAfter() {
		System.out.println("after   ------------------");
	}

	@MyAround("com.reyco.test.core.service")
	public void testAround() throws Throwable {
		System.out.println("around   -----------------");
	}

}
