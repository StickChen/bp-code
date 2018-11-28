package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;


public class DisruptorPerformanceTest {

    /**
     * 每一场景的测试的重复次数；
     */
    private static final int TEST_TIMES = 5;

    /**
     * 测试数据量的规模；即要发布和处理的事件的总数；
     */
    private static final int DATA_COUNT = 1024 * 1024;

    public static void main(String[] args) {
        try {
            // 针对“单一生产者、单一消费者”场景的测试；
            testOneP_OneC();
        } catch (Exception e) {
            System.out.println("发生错误！--" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试：单一生产者, 单一消费者；
     * 
     * @throws Exception
     */
    private static void testOneP_OneC() throws Exception {
        // 基准测试：在事件发布线程直接调用事件处理器，没有线程间的数据交换；
        testOneP_OneC_base();

        // 对比测试1：通过 ArrayBlockingQueue 实现数据在发布线程和处理线程的交换；
        testOneP_OneC_blockingQueue();

        // 对比测试2：通过 Disruptor 实现数据在发布线程和处理线程的交换；
        testOneP_OneC_disruptor();
    }

    private static void testOneP_OneC_base() throws Exception {
        System.out.println("【基准测试】");
        for (int i = 0; i < TEST_TIMES; i++) {
            System.out.print("[" + (i + 1) + "]--");

            testOneP_OneC(new PublisherFactory() {

                @Override
                public EventPublisher newInstance(PublisherCreationArgs arg) {
                    return new DirectingPublisher(arg.getHandler());
                }
            }, new SimpleTracerFactory());
        }
    }

    private static void testOneP_OneC_blockingQueue() throws Exception {
        System.out.println("【对比测试1: ArrayBlockingQueue 实现】");
        for (int i = 0; i < TEST_TIMES; i++) {
            System.out.print("[" + (i + 1) + "]--");

            testOneP_OneC(new BlockingArrayPublisherFactory(), new SimpleTracerFactory());
        }
    }

    private static void testOneP_OneC_disruptor() throws Exception {
        System.out.println("【对比测试2: Disruptor实现】");
        for (int i = 0; i < TEST_TIMES; i++) {
            System.out.print("[" + (i + 1) + "]--");

            testOneP_OneC(new DisruptorPublisherFactory(), new SimpleTracerFactory());
        }
    }


    private static void testOneP_OneC(
            ObjectFactory<EventPublisher, PublisherCreationArgs> publisherFactory,
            ObjectFactory<CounterTracer, Integer> tracerFactory) throws Exception {
        CounterTracer tracer = tracerFactory.newInstance(DATA_COUNT);// 计数跟踪到达指定的数值；
        TestHandler handler = new TestHandler(tracer);// Consumer 的事件处理；

        EventPublisher publisher =
                publisherFactory.newInstance(new PublisherCreationArgs(DATA_COUNT, handler));// 通过工厂对象创建不同的
                                                                                             // Producer
                                                                                             // 的实现；
        publisher.start();
        tracer.start();

        // 发布事件；
        for (int i = 0; i < DATA_COUNT; i++) {
            publisher.publish(i);
        }

        // 等待事件处理完成；
        tracer.waitForReached();

        publisher.stop();

        // 输出结果；
        printResult(tracer);
    }

    private static void printResult(CounterTracer tracer) {
        long timeSpan = tracer.getMilliTimeSpan();
        if (timeSpan > 0) {
            long throughputPerSec = DATA_COUNT * 1000 / tracer.getMilliTimeSpan();
            System.out.println(String.format("每秒吞吐量：%s；(%s/%sms)", throughputPerSec, DATA_COUNT,
                    tracer.getMilliTimeSpan()));
        } else {
            System.out.println(String.format("每秒吞吐量：--；(%s/%sms)", DATA_COUNT,
                    tracer.getMilliTimeSpan()));
        }
    }

}
