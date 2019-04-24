package com.shallop.bpc.collection.basic.jvm;

import java.io.IOException;

/**
 * @author chenxuanlong
 * @date 2017/3/30
 */
public class TestAgent {

	public static void main(String[] args) throws IOException {
		TestAgent ta = new TestAgent();
		ta.test();
		while (true) {
		
		}
	}

	public void test() {
		System.out.println("I'm TestAgent");
	}

}
