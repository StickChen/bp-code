package com.shallop.bpc.collection.basic.jvm.reflect;

import java.lang.reflect.Method;

/**
*
 * @author chenxuanlong
 * @date 2017/9/1
 */
public class ReflectCase {

	public static void main(String[] args) throws Exception {
		Proxy target = new Proxy();
		Method method = Proxy.class.getDeclaredMethod("run");
		method.invoke(target);
	}

	static class Proxy {
		public void run() {
			System.out.println("run");
		}
	}
}
