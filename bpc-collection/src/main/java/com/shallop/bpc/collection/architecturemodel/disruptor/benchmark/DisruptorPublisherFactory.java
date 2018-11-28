package com.shallop.bpc.collection.architecturemodel.disruptor.benchmark;

public class DisruptorPublisherFactory implements ObjectFactory<EventPublisher, PublisherCreationArgs>{

	@Override
	public EventPublisher newInstance(PublisherCreationArgs arg) {
		DisruptorPublisher publisher = new DisruptorPublisher(arg.getMaxEventSize(), arg.getHandler());
		return publisher;
	}

}
