package com.shallop.bpc.collection.basic.aop;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenxuanlong
 * @date 2016/3/7
 */
public class AopDemo {

	private final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-aop.xml");

	@Test
	public void test1() {

		AopService aopService = context.getBean(AopService.class);
		String result = aopService.echo("haha");
		System.out.println("result=" + result);

	}


}
