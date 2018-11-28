package com.shallop.bpc.collection.basic.aop;

import org.springframework.stereotype.Service;

/**
 * @author chenxuanlong
 * @date 2016/3/7
 */
@Service
public class AopServiceImpl implements AopService {


	@Override
	public String echo(String param) {

		System.out.println("invoke AopServiceImpl echo param=" + param);

		return "invoke echo success " + param;
	}

}
