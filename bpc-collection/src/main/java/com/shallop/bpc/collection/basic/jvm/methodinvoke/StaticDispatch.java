package com.shallop.bpc.collection.basic.jvm.methodinvoke;

/**
 * 静态分派
 */
public class StaticDispatch {

	static abstract class Human {

	}

	static class Man extends Human {

	}

	static class Woman extends Human {

	}

	public void sayHello(Human guy) {
		System.out.println("hello guy...");
	}

	public void sayHello(Man man) {
		System.out.println("hello man...");
	}

	public void sayHello(Woman woman) {
		System.out.println("hello woman...");
	}

	public static void main(String[] args) {
		Human man = new Man();		// 静态类型  外观类型
		Human woman = new Woman();
		StaticDispatch sd = new StaticDispatch();
		sd.sayHello((Man) man);		// 编译器在重载时是通过参数的静态类型而不是实际类型作为判定依据的
		sd.sayHello(woman);		//
	}
}
