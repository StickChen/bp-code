package com.shallop.bpc.collection.architecturemodel.disruptor.demo;

import com.lmax.disruptor.EventFactory;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class LongEventFactory implements EventFactory {
	@Override
	public Object newInstance() {
		return new LongEvent();
	}
}
