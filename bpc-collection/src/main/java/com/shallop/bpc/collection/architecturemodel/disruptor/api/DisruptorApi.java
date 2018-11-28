package com.shallop.bpc.collection.architecturemodel.disruptor.api;

import com.lmax.disruptor.*;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class DisruptorApi {

	public static void main(String[] args) {
		test1();
	}

	public static void test1(){

	}

}

class ValueEvent {
	private byte[] packet;

	public byte[] getValue()
	{
		return packet;
	}

	public void setValue(final byte[] packet)
	{
		this.packet = packet;
	}

	public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>()
	{
		public ValueEvent newInstance()
		{
			return new ValueEvent();
		}
	};
}