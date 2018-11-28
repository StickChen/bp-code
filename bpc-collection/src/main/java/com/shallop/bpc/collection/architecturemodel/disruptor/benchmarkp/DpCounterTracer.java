package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public interface DpCounterTracer {
	void start();

	void waitForReached() throws InterruptedException;

	long getMilliTimeSpan();

	boolean count();
}
