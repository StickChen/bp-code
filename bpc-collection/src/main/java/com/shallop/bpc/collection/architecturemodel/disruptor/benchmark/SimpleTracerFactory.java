package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

public class SimpleTracerFactory implements ObjectFactory<CounterTracer, Integer> {

	@Override
	public CounterTracer newInstance(Integer maxCount) {
		return new SimpleTracer(maxCount);
	}


}
