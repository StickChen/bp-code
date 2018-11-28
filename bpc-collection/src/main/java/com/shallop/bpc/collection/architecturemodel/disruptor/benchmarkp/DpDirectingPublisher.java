package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DpDirectingPublisher implements DpEventPublisher {

	private final DpTestHandler handler;

	private DpTestEvent event = new DpTestEvent();


	public DpDirectingPublisher(DpTestHandler handler) {
		this.handler = handler;
	}

	@Override
	public void start() {

	}

	@Override
	public void publish(int data) throws InterruptedException {
		event.setValue(data);
		handler.process(event);
	}

	@Override
	public void stop() {

	}
}
