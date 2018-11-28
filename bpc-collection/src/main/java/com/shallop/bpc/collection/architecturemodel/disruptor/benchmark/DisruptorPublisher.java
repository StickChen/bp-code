package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DisruptorPublisher implements EventPublisher {

	private class TestEventHandler implements EventHandler<TestEvent> {

		private TestHandler handler;

		public TestEventHandler(TestHandler handler) {
			this.handler = handler;
		}

		@Override
		public void onEvent(TestEvent event, long sequence, boolean endOfBatch)
				throws Exception {
			handler.process(event);
		}

	}

	private static class TestEventFactory implements EventFactory<TestEvent> {

		@Override
		public TestEvent newInstance() {
			return new TestEvent();
		}

	}

	private static EventFactory<TestEvent> EVENT_FACTORY = new TestEventFactory();

	private Disruptor<TestEvent> disruptor;

	private TestEventHandler handler;

	private RingBuffer<TestEvent> ringbuffer;
	
	private ExecutorService executor;
	
	private static final WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
	private static final WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
	private static final WaitStrategy LITEBLOCKING_WAIT = new LiteBlockingWaitStrategy();
	private static final WaitStrategy TIMEOUTBLOCKING_WAIT = new TimeoutBlockingWaitStrategy(1, TimeUnit.MILLISECONDS);
	private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
	private static final WaitStrategy PHASEDBACKOFF_WAIT = new PhasedBackoffWaitStrategy(100, 10, TimeUnit.NANOSECONDS, YIELDING_WAIT);
	private static final WaitStrategy BUSYSPIN_WAIT = new BusySpinWaitStrategy();

	public DisruptorPublisher(int bufferSize, TestHandler handler) {
		this.handler = new TestEventHandler(handler);
		executor = Executors.newSingleThreadExecutor();
		disruptor = new Disruptor<TestEvent>(EVENT_FACTORY, bufferSize,
				executor, ProducerType.SINGLE,
				YIELDING_WAIT);
	}

	@SuppressWarnings("unchecked")
	public void start() {
		disruptor.handleEventsWith(handler);
		disruptor.start();
		ringbuffer = disruptor.getRingBuffer();
	}

	@Override
	public void publish(int data){
		long seq = ringbuffer.next();
		try {
			TestEvent evt = ringbuffer.get(seq);
			evt.setValue(data);
		} finally {
			ringbuffer.publish(seq);
		}
	}

	@Override
	public void stop() {
		disruptor.shutdown();
		executor.shutdown();
	}

}
