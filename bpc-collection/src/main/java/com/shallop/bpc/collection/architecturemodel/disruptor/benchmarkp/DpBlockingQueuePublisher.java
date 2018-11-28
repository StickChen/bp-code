package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DpBlockingQueuePublisher implements DpEventPublisher {

	private final LinkedBlockingQueue<DpTestEvent> queue;
	private final DpTestHandler handler;

	public DpBlockingQueuePublisher(int maxEventSize, DpTestHandler handler) {
		queue = new LinkedBlockingQueue<DpTestEvent>(maxEventSize);
		this.handler = handler;
	}

	@Override
	public void start() {
		new Thread(new Runnable() {
			@Override public void run() {
				handler();

			}
		}).start();
	}

	private void handler() {

		try {
			while (true) {
				DpTestEvent evt = queue.take();
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
		DpTestEvent evt = new DpTestEvent();
		evt.setValue(data);
		queue.put(evt);
	}

	@Override
	public void stop() {
	}
}
