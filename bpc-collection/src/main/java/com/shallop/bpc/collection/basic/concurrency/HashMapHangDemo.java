package com.shallop.bpc.collection.basic.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author chenxuanlong
 * @date 20/30
 */
public class HashMapHangDemo {

	private Map<Integer, Object> holder = new HashMap<Integer, Object>();

	public static void demonstrate() {

		HashMapHangDemo demo = new HashMapHangDemo();
		for (int i = 0; i < 100; i++) {
			demo.holder.put(i, null);
		}

		Thread thread = new Thread(demo.getConcurrencyCheckTask());
		thread.start();
		thread = new Thread(demo.getConcurrencyCheckTask());
		thread.start();
		System.out.println("Start get in main!");
		for (int i = 0; ; ++i) {
			for (int j = 0; j < 10000; j++) {
				demo.holder.get(j);

				//
				System.out.printf("Get key %s in round %s\n", j, i);
			}
		}
	}

	private ConcurrencyTask getConcurrencyCheckTask() {
		return new ConcurrencyTask();
	}

	private class ConcurrencyTask implements Runnable{

		private Random random = new Random();

		@Override
		public void run() {
			System.out.println("Add loop started in task!");
			while (true) {
				holder.put(random.nextInt() % (1024 * 1024 * 100), null);
			}
		}
	}
}
