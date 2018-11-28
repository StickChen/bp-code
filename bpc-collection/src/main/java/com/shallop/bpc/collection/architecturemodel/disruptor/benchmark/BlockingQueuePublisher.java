package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueuePublisher implements EventPublisher {
	
	private ArrayBlockingQueue<TestEvent> queue ;
	
	private TestHandler handler;
	
	public BlockingQueuePublisher(int maxEventSize, TestHandler handler) {
		this.queue = new ArrayBlockingQueue<TestEvent>(maxEventSize);
		this.handler = handler;
	}

	public void start(){
		Thread thrd = new Thread(new Runnable() {
			@Override
			public void run() {
				handle();
			}
		});
		thrd.start();
	}
	
	private void handle(){
		try {
			TestEvent evt ;
			while (true) {
				evt = queue.take();
				if (evt != null && handler.process(evt)) {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(int data) throws InterruptedException {
		TestEvent evt = new TestEvent();
		evt.setValue(data);
		queue.put(evt);
	}

	@Override
	public void stop() {
	}

}
