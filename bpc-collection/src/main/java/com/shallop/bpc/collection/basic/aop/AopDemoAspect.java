package com.shallop.bpc.collection.basic.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenxuanlong
 * @date 2016/3/8
 */
@Aspect
@Component
public class AopDemoAspect {

	@Autowired
	private AopService aopService;

	@Around("execution(* com.shallop.bpc.collection.basic.aop.AopService.echo(..))")
	public Object methodAop(ProceedingJoinPoint point) throws Throwable {
		Object[] args = point.getArgs();
		System.out.println("AOP-"+ JSON.toJSON(args));
		Object result = point.proceed(args);
		System.out.println("AOP-"+JSON.toJSON(result));
		return result;
	}


}
