package com.shallop.bpc.collection.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.shallop.bpc.collection.serviceframework.spring.AopTargetUtils;
import com.shallop.bpc.collection.serviceframework.spring.EchoService;
import com.shallop.bpc.collection.serviceframework.spring.SpringBeanDemoService;

/**
 * @author chenxuanlong
 * @date 2016/6/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-beans.xml"})
public class MockitoSpringDemo {

	@Mock
	private EchoService spyEchoService;
	@Autowired
	private EchoService echoService;

	@Autowired
	private SpringBeanDemoService springBeanDemoService;

	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
		// Spring AOP 代理对象
	}

	@After
	public void clearMocks() throws Exception {
		// 还原现场，避免影响其他测试用例
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(springBeanDemoService), "echoService", echoService);
	}

	@Test
	public void testEcho() throws Exception {

		String wecan = springBeanDemoService.echo("wecan");
		System.out.println(wecan);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(springBeanDemoService), "echoService", spyEchoService);
		Mockito.when(spyEchoService.echo("docan")).thenReturn("woghweo");
		String docan = springBeanDemoService.echo("docan");
		System.out.println(docan);
	}
	
}
