package com.shallop.bpc.collection.basic.jdk8;

/**
 * @author chenxuanlong
 * @date 2017/4/7
 */
public class Value<T> {
	public static <T> T defaultValue() {
		return null;
	}

	public T getOrDefault(T value, T defaultValue) {
		return (value != null) ? value : defaultValue;
	}
}
