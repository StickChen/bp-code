package com.shallop.bpc.collection.architecturemodel.disruptor.benchmarkp;


/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DpPublisherCreationArgs {

	private int maxEventSize;

	private DpTestHandler handler;

	public DpPublisherCreationArgs(int maxEventSize, DpTestHandler handler) {
		this.maxEventSize = maxEventSize;
		this.handler = handler;
	}

	public int getMaxEventSize() {
		return maxEventSize;
	}

	public void setMaxEventSize(int maxEventSize) {
		this.maxEventSize = maxEventSize;
	}

	public DpTestHandler getHandler() {
		return handler;
	}

	public void setHandler(DpTestHandler handler) {
		this.handler = handler;
	}
}
