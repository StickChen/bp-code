package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DpTestHandler {

	private final DpCounterTracer tracer;

	public DpTestHandler(DpCounterTracer tracer) {
		this.tracer = tracer;
	}

	public boolean process(DpTestEvent event){
		return tracer.count();
	}
}
