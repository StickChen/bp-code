package com.shallop.bpc.collection.serviceframework.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringBeanDemoServiceTest {

	private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"/spring-beans.xml");

	@Test
	public void test1() {

		SpringBeanDemoService springBeanDemoService = context.getBean(SpringBeanDemoService.class);
		List<String> paramList = springBeanDemoService.getParamList();
		System.out.println(paramList + "" + paramList.size());

	}

}