package com.shallop.bpc.collection.basic.concurrency.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * https://leokongwq.github.io/2017/01/13/java-LockSupport.html
 * @author chenxuanlong
 * @date 2017/8/12
 */
public class FIFOMutex {
	private final AtomicBoolean locked = new AtomicBoolean(false);
	private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

	public void lock() {
		boolean wasInterrupted = false;
		Thread current = Thread.currentThread();
		waiters.add(current);

		// Block while not first in queue or cannot acquire lock
		while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
			LockSupport.park(this);
			if (Thread.interrupted()) // ignore interrupts while waiting
				wasInterrupted = true;
		}

		waiters.remove();
		if (wasInterrupted)  // reassert interrupt status on exit
			current.interrupt();
	}

	public void unlock() {
		locked.set(false);
		LockSupport.unpark(waiters.peek());
	}
}
