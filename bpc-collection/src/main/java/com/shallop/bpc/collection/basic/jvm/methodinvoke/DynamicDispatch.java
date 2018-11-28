package com.shallop.bpc.collection.basic.jvm.methodinvoke;

/**
 * @author chenxuanlong
 * @date 2017/8/31
 */

public class DynamicDispatch {
	static abstract class Human {
		protected abstract void sayHello();
	}

	static class Man extends Human {
		@Override
		protected void sayHello() {
			System.out.println("man say hello");
		}
	}

	static class Woman extends Human {
		@Override
		protected void sayHello() {
			System.out.println("woman say hello");
		}
	}

	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		man.sayHello();		// 按实际类型
		woman.sayHello();		//
		man = new Woman();
		man.sayHello();
	}
}
