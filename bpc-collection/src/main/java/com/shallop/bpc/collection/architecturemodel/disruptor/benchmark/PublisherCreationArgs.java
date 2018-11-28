package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

public class PublisherCreationArgs {

	private int maxEventSize;

	private TestHandler handler;
	
	public PublisherCreationArgs(int maxEventSize, TestHandler handler) {
		this.maxEventSize = maxEventSize;
		this.handler = handler;
	}
	
	

	public int getMaxEventSize() {
		return maxEventSize;
	}

	public TestHandler getHandler() {
		return handler;
	}

}
