package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

public interface CounterTracer {

    public abstract void start();

    public abstract long getMilliTimeSpan();

    /**
     * 计数器加1；
     * 
     * 如果已达到最大的跟踪目标数，则返回 true；否则返回 false；
     * 
     * @return
     */
    public abstract boolean count();

    public abstract void waitForReached() throws InterruptedException;

}
