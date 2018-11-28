package com.shallop.bpc.collection.serviceframework.spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chenxuanlong
 * @date 2016/3/8
 */
//@Service
public class SpringBeanDemoService {

	private List<String> paramList;

	@Autowired
	private EchoService echoService;

	public String echo(String echo) {
		String echo1 = echoService.echo(echo);
		return "SpringBeanDemoService:" + echo1;
	}

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}
}
