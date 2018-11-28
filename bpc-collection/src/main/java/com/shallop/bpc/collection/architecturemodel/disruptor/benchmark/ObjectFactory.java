package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

public interface ObjectFactory<T, TA> {

	T newInstance(TA arg);
}
