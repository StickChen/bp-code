package com.shallop.bpc.collection.architecturemodel.disruptor.practice;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author StickChen
 * @date 2016/5/21
 */
public class TaskDispatchApp {
	
	public static void main(String[] args) throws InterruptedException {
		Executor executor = Executors.newCachedThreadPool();
		
		EventFactory eventFactory = new EventFactory() {
			@Override
			public Object newInstance() {
				return new DpTask();
			}
		};
		
		int bufferSize = 1024;
		Disruptor<DpTask> dpTaskDisruptor = new Disruptor<DpTask>(eventFactory, bufferSize, executor);
		dpTaskDisruptor.handleEventsWith(new EventHandler<DpTask>() {
			// 消费者
			@Override
			public void onEvent(DpTask dpTask, long l, boolean b) throws Exception {
				System.out.println("receive msg=" + dpTask.getMsg() + ",l=" + l + ",b=" + b);
			}
		});
		dpTaskDisruptor.start();
		RingBuffer<DpTask> ringBuffer = dpTaskDisruptor.getRingBuffer();

		DpTaskProducer dpTaskProducer = new DpTaskProducer(ringBuffer);

		ByteBuffer bb = ByteBuffer.allocate(8);
		for (long i = 0; true; i++) {
			bb.putLong(0, i);
			dpTaskProducer.sendMsg(bb);
			Thread.sleep(1000);
		}
	}
	
}

class DpTask{
	
	private String msg;
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}

class DpTaskProducer{

	private final RingBuffer<DpTask> ringBuffer;

	public DpTaskProducer(RingBuffer<DpTask> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void sendMsg(ByteBuffer bb){
		long sequence = ringBuffer.next();

		if (ringBuffer.remainingCapacity() < ringBuffer.getBufferSize() * 0.1) {
			System.out.println("disruptor:ringbuffer avaliable capacity is less than 10 %");
		}
		String msg = null;
		try {
			DpTask dpTask = ringBuffer.get(sequence);
			msg = String.valueOf(bb.getLong(0));
			dpTask.setMsg(msg);
		} finally {
			ringBuffer.publish(sequence);
			System.out.println("send msg=" + msg);
		}

	}
}