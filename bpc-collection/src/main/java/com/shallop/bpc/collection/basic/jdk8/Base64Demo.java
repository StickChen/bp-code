package com.shallop.bpc.collection.basic.jdk8;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author chenxuanlong
 * @date 2017/4/7
 */
public class Base64Demo {

	@Test
	public void test1() {
		final String text = "Base64 finally in Java 8!";

		final String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
		System.out.println(encoded);

		final String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
		System.out.println(decoded);
	}

}
