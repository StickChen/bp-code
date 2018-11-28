package com.shallop.bpc.collection.architecturemodel.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class LongEventHandler implements EventHandler<LongEvent> {
	@Override
	public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
		System.out.println(longEvent.getValue());
	}
}
