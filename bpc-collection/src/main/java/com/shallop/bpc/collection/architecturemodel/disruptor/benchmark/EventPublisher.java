package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

/**
 *
 * 
 * @author haiq
 *
 */
public interface EventPublisher {
	
	public void start();
	
	public void stop();
	
	public void publish(int data) throws InterruptedException;
}
