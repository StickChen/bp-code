package com.shallop.bpc.collection.basic.concurrency;

public class VolatileDemo2 {
	public static boolean b;
	public static volatile int v;

	public static void main(String[] args) throws InterruptedException {
		Thread other = new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (b) {
						if (v != 1) {
							System.out.println("v!=" + v);
						}
						System.out.println("v=" + v);
						break;
					}
				}
			}
		});
		Thread one = new Thread(new Runnable() {
			public void run() {
				v = 1;
				b = true;		// 可重排
			}
		});
		other.start();
		one.start();
		other.join();
		one.join();

	}

}
