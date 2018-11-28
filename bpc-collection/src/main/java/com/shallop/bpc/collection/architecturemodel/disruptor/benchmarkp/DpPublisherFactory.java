package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public interface DpPublisherFactory<Req, Rsp> {

	Rsp newInstance(Req arg);

}
