package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

import java.util.concurrent.CountDownLatch;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DpSimpleTracer implements DpCounterTracer {

	private final long expectedCount;
	private long startTicks;
	private boolean end;
	private long count;
	private long endTicks;
	private CountDownLatch latch = new CountDownLatch(1);

	public DpSimpleTracer(long expectedCount) {
		this.expectedCount = expectedCount;
	}

	@Override
	public void start() {
		startTicks = System.currentTimeMillis();
		end = false;
	}

	@Override
	public void waitForReached() throws InterruptedException {
		latch.await();
	}

	@Override
	public long getMilliTimeSpan() {
		return endTicks - startTicks;
	}

	@Override
	public boolean count() {
		if (end) {
			return end;
		}
		count++;
		end = count >= expectedCount;
		if (end){
			endTicks = System.currentTimeMillis();
			latch.countDown();
		}
		return end;
	}

}
