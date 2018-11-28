package com.shallop.bpc.collection.basic.java;

/**
 * @author StickChen
 * @date 2016/3/20
 */
public class ExtendApp {

	public static void main(String[] args) {
		SuperClass subClass = new SubClass(1);
		System.out.println(subClass.a);

	}

}
