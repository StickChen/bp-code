package com.shallop.bpc.collection.serviceframework.spring;

import org.springframework.stereotype.Service;

/**
 * @author chenxuanlong
 * @date 2016/6/6
 */
@Service
public class EchoService {

	public String echo(String param) {
		System.out.println("EchoService:" + param);
		return "EchoService return" + param;
	}

}
