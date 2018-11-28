package com.shallop.bpc.collection.basic;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author chenxuanlong
 * @date 2016/3/24
 */
public class QueueTest {

	@Test
	public void test1() {
		Queue<String> strings = new LinkedList<>();
		strings.offer("a");
		strings.offer("b");
		System.out.println(strings);
	}

}
