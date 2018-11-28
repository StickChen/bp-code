package com.shallop.bpc.collection.basic.aop;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author chenxuanlong
 * @date 2017/9/1
 */
class Target {
	public void f() {
		System.out.println("Target f()");
	}

	public void g() {
		System.out.println("Target g()");
	}
}

class Interceptor implements MethodInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("I am intercept begin");
		// Note: 此处一定要使用proxy的invokeSuper方法来调用目标类的方法
		proxy.invokeSuper(obj, args);
		System.out.println("I am intercept end");
		return null;
	}
}

public class CglibDemo {
	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\temp_code");
		// 实例化一个增强器，也就是cglib中的一个class generator
		Enhancer eh = new Enhancer();
		// 设置目标类
		eh.setSuperclass(Target.class);
		// 设置拦截对象
		eh.setCallback(new Interceptor());
		// 生成代理类并返回一个实例
		Target t = (Target) eh.create();
		t.f();
		t.g();
	}
}
