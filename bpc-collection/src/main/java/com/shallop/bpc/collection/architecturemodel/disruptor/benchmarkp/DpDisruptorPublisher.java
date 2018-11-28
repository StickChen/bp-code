package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DpDisruptorPublisher implements DpEventPublisher {

	private final DpTestEventHandler handler;
	private final ExecutorService executor;
	private final Disruptor<DpTestEvent> disruptor;

	private static final WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
	private static final WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
	private static final WaitStrategy LITEBLOCKING_WAIT = new LiteBlockingWaitStrategy();
	private static final WaitStrategy TIMEOUTBLOCKING_WAIT = new TimeoutBlockingWaitStrategy(1, TimeUnit.MILLISECONDS);
	private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
	private static final WaitStrategy PHASEDBACKOFF_WAIT = new PhasedBackoffWaitStrategy(100, 10, TimeUnit.NANOSECONDS,
			YIELDING_WAIT);
	private static final WaitStrategy BUSYSPIN_WAIT = new BusySpinWaitStrategy();
	private static final EventFactory<DpTestEvent> eventFactory = new EventFactory<DpTestEvent>() {
		@Override
		public DpTestEvent newInstance() {
			return new DpTestEvent();
		}
	};
	private RingBuffer<DpTestEvent> ringBuffer;
	
	public DpDisruptorPublisher(int maxEventSize, DpTestHandler handler) {
		this.handler = new DpTestEventHandler(handler);
		executor = Executors.newCachedThreadPool();
		disruptor = new Disruptor<DpTestEvent>(eventFactory, maxEventSize, executor, ProducerType.SINGLE, SLEEPING_WAIT);
	}

	@Override
	public void start() {
		disruptor.handleEventsWith(handler);
		disruptor.start();
		ringBuffer = disruptor.getRingBuffer();
	}

	@Override
	public void publish(int data) {
		long seq = ringBuffer.next();

		try {
			DpTestEvent evt = ringBuffer.get(seq);
			evt.setValue(data);
		} finally {
			ringBuffer.publish(seq);
		}

	}

	@Override
	public void stop() {
		disruptor.shutdown();
		executor.shutdown();
	}

	private class DpTestEventHandler implements EventHandler<DpTestEvent> {

		private final DpTestHandler handler;

		public DpTestEventHandler(DpTestHandler handler) {
			this.handler = handler;
		}

		@Override
		public void onEvent(DpTestEvent event, long sequence, boolean endOfBatch) throws Exception {
			handler.process(event);
		}
	}
}
