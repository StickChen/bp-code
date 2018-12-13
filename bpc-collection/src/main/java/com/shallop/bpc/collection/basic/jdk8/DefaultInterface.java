package com.shallop.bpc.collection.basic.jdk8;

/**
 * @author chenxuanlong
 * @date 2018/12/12
 */
@FunctionalInterface
public interface DefaultInterface {

    void run();

    default void defaultMethod() {
        System.out.println("defaultMethod");
    }
    default void defaultMethod2() {
        System.out.println("defaultMethod");
    }

    static void staticMethod() {
        System.out.println("staticMethod");
    }
}
