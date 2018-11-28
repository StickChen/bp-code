package com.shallop.bpc.collection.basic.jvm;

import java.lang.instrument.Instrumentation;

/**
 * @author chenxuanlong
 * @date 2017/3/30
 */
public class AgentMain {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("Hi, I'm agent!");
		inst.addTransformer(new TestTransformer());
	}

}
