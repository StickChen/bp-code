package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public interface DpEventPublisher {

	void start();

	void publish(int i) throws InterruptedException;

	void stop();
}
