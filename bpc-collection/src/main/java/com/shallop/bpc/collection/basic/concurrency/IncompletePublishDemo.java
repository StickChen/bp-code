package com.shallop.bpc.collection.basic.concurrency;

/**
 * @author chenxuanlong
 * @date 2016/8/25
 */
// 不安全发布
public class IncompletePublishDemo {

	volatile boolean stop = false;

	public static void main(String[] args) throws InterruptedException {
		demonstrate();
	}

	public static void demonstrate() throws InterruptedException {
		IncompletePublishDemo demo = new IncompletePublishDemo();
		Thread thread = new Thread(demo.new ConcurrencyVolatileCheckTask());
		thread.start();

		Thread.sleep(1000);
		System.out.println("Set stop to true in main!");
		demo.stop = true;
		System.out.println("Exit main.");
	}

	class ConcurrencyVolatileCheckTask implements Runnable{

		@Override
		public void run() {
			System.out.println("ConcurrencyVolatileCheckTask started!");
			// 如果主线程中的stop的值是可见的，则循环会立即退出
			// 用server模式启动则必然出现这个问题，因为此模式下JVM将判断条件提升到循环体外部了
			long i = 0;
			while (!stop) {
				i++;
			}
			System.out.println("ConcurrencyVolatileCheckTask stopped! cycle: " + i);
		}
	}
	
}
