package com.shallop.bpc.collection.basic.concurrency.volatile_demo;

public class VolatileDemo3 {
	public static int a;
	public static boolean b;
	public static volatile int v = 1;

	public static void main(String[] args) throws InterruptedException {
		while (true){
			Thread one = new Thread(new Runnable() {
				public void run() {
					b = true;		// 可重排
					a = v;
				}
			});
			one.start();
			while (true) {
				if (a == 1) {
					if (!b) {
						System.out.println("b=" + b);
						System.exit(0);
					}
					break;
				}
			}
			one.join();
		}

	}

}
