package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DisruptorBenchmarkApp {

	/**
	 * 每一场景的测试的重复次数；
	 */
	private static final int TEST_TIMES = 5;
	/**
	 * 测试数据量的规模；即要发布和处理的事件的总数；
	 */
	private static final int DATA_COUNT = 1024 * 1024;

	public static void main(String[] args) throws InterruptedException {
		testBase();
		testBlockingQueu();
		testDisruptor();
	}

	private static void testBase() throws InterruptedException {
		System.out.println("【基准测试】");
		testQueue(new DpPublisherFactory<DpPublisherCreationArgs, DpEventPublisher>() {
			@Override
			public DpEventPublisher newInstance(DpPublisherCreationArgs arg) {
				return new DpDirectingPublisher(arg.getHandler());
			}
		}, TEST_TIMES);
	}

	private static void testBlockingQueu() throws InterruptedException {
		System.out.println("【对比测试1: ArrayBlockingQueue 实现】");
		testQueue(new DpPublisherFactory<DpPublisherCreationArgs, DpEventPublisher>() {
			@Override
			public DpEventPublisher newInstance(DpPublisherCreationArgs arg) {
				return new DpBlockingQueuePublisher(arg.getMaxEventSize(), arg.getHandler());
			}
		}, TEST_TIMES);
	}

	private static void testDisruptor() throws InterruptedException {
		System.out.println("【对比测试2: Disruptor实现】");
		testQueue(new DpPublisherFactory<DpPublisherCreationArgs, DpEventPublisher>() {
			@Override
			public DpEventPublisher newInstance(DpPublisherCreationArgs arg) {
				return new DpDisruptorPublisher(arg.getMaxEventSize(), arg.getHandler());
			}
		}, TEST_TIMES);
	}

	private static void testQueue(DpPublisherFactory<DpPublisherCreationArgs, DpEventPublisher> dpPublisherFactory, int testTimes)
			throws InterruptedException {

		for (int i = 0; i < testTimes; i++) {
			System.out.print("[" + (i + 1) + "]--");
			testQueue(dpPublisherFactory);
		}

	}

	private static void testQueue(DpPublisherFactory<DpPublisherCreationArgs, DpEventPublisher> dpPublisherFactory)
			throws InterruptedException {
		DpCounterTracer tracer = new DpSimpleTracer(DATA_COUNT);
		DpTestHandler handler = new DpTestHandler(tracer);  // 消费

		DpEventPublisher publisher = dpPublisherFactory.newInstance(new DpPublisherCreationArgs(DATA_COUNT, handler));

		publisher.start();
		tracer.start();

		for (int i = 0; i < DATA_COUNT; i++) {
			publisher.publish(i);
		}

		tracer.waitForReached();

		publisher.stop();

		printResult(tracer);
	}

	private static void printResult(DpCounterTracer tracer) {
		long timeSpan = tracer.getMilliTimeSpan();
		if (timeSpan > 0) {
			long throughputPerSec = DATA_COUNT * 1000 / tracer.getMilliTimeSpan();
			System.out.println(String.format("每秒吞吐量：%s；(%s/%sms)", throughputPerSec, DATA_COUNT, tracer.getMilliTimeSpan()));
		} else {
			System.out.println(String.format("每秒吞吐量：--；(%s/%sms)", DATA_COUNT, tracer.getMilliTimeSpan()));
		}
	}

}
