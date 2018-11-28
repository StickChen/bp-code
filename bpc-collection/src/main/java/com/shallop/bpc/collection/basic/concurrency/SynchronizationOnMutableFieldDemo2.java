package com.shallop.bpc.collection.basic.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 在易变域上的同步
 * 
 * @author chenxuanlong
 * @date 2016/10/13
 */
public class SynchronizationOnMutableFieldDemo2 {

	static final int ADD_COUNT = 10000;
	private volatile List<Listener> listeners = new CopyOnWriteArrayList<>();

	public static void demostrate() throws InterruptedException {
		SynchronizationOnMutableFieldDemo2 demo2 = new SynchronizationOnMutableFieldDemo2();
		Thread thread1 = new Thread(demo2.getConcurrencyCheckTask());
		thread1.start();
		Thread thread2 = new Thread(demo2.getConcurrencyCheckTask());
		thread2.start();
		
		thread1.join();
		thread2.join();

		int actualSize = demo2.listeners.size();
		int expectedSize = ADD_COUNT * 2;
		if (actualSize != expectedSize) {
			// 在我的开发机上，几乎必现！（简单安全的解法：final List字段并用并发安全的List，如CopyOnWriteArrayList）
			System.err.printf("Fuck! Lost update on mutable field! actual %s expected %s.\n", actualSize, expectedSize);
		}else {
			System.out.println("Emm... Got right answer!!");
		}
	}

	public void addListener(Listener listener) {
		synchronized (listeners){
			ArrayList<Listener> results = new ArrayList<>(this.listeners);
			results.add(listener);
			listeners = results;
		}
	}
	
	ConcurrencyCheckTask getConcurrencyCheckTask() {
		return new ConcurrencyCheckTask();
	}

	
	
	
	private class ConcurrencyCheckTask implements Runnable {

		@Override
		public void run() {
			System.out.println("ConcurrencyCheckTask started!");
			for (int i = 0; i < ADD_COUNT; i++) {
				addListener(new Listener());
			}
			System.out.println("ConcurrencyCheckTask stopped!");
		}
	}

	static class Listener {
	}
}
