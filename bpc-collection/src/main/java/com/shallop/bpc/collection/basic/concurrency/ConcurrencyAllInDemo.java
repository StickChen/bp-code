package com.shallop.bpc.collection.basic.concurrency;

import org.junit.Test;

/**
 * @author chenxuanlong
 * @date 2016/8/26
 */
// 并发编程demo集合
public class ConcurrencyAllInDemo {

	// 安全发布，无同步的修改在另一个线程中会读不到
	@Test
	public void testVolatile() throws InterruptedException {
		IncompletePublishDemo.demonstrate();
	}

	// hashmap 并发死循环
	@Test
	public void testHashMap(){
		HashMapHangDemo.demonstrate();
	}

	// long变量读到无效值
	@Test
	public void testInvalidLongDemo(){
		InvalidLongDemo2.demonstrate();
	}

	// 无同步的并发计数结果不对
	@Test
	public void testWrongCounter() throws InterruptedException {

		ConcurrencyCounterDemo.demostrate();

	}

	// 在易变域上的同步
	@Test
	public void testSynchronizationOnMutableField() throws InterruptedException {

		SynchronizationOnMutableFieldDemo2.demostrate();

	}

	// 对称锁死锁


}























