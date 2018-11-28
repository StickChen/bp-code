package com.shallop.bpc.collection.basic.jdk8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author chenxuanlong
 * @date 2017/4/7
 */
public class ParameterNames {


	// 方法参数的名字能保留在Java字节码中
	public static void main(String[] args) throws Exception {
		Method method = ParameterNames.class.getMethod("main", String[].class);
		for (final Parameter parameter : method.getParameters()) {
			boolean namePresent = parameter.isNamePresent();
			System.out.println("namePresent: " + namePresent);	// 是否可以获取参数的名字
			System.out.println("Parameter: " + parameter.getName());
		}
	}
}