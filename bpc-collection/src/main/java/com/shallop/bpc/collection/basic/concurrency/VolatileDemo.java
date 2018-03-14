package com.shallop.bpc.collection.basic.concurrency;

public class VolatileDemo {
	private volatile int a = 0;
	private volatile boolean b = false;

	public void read() {
		a = 1;
		b = true;
	}

	public void write() {
		if (b) {
			int i;

			i = a * a;

			if (i != 1 && i != 4)
				System.out.println(a + "-" + i);
			a = 2;
			b = false;
		}
	}

	public static void main(String[] args) {
		final VolatileDemo t = new VolatileDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
                    t.read();
                }
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
                    t.write();
                }
			}
		}).start();

	}
}
