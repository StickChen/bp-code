package com.shallop.bpc.collection.basic.jdk8;

/**
 * @author chenxuanlong
 * @date 2018/12/12
 */
public class FunctionAnd {
    public static void main(String[] args) {
        Object o = (I & J) () -> {};
    }
}
@FunctionalInterface
interface I {
    void foo();
}
@FunctionalInterface
interface J {
    void foo();
}
@FunctionalInterface
interface A {
    void sameMethod();

    default void defaultMethod() {

    }
    default void defaultMethodA() {

    }
}
@FunctionalInterface
interface B {
    void sameMethod();

    default void defaultMethod() {

    }
    default void defaultMethodB() {

    }
}
class AB implements A,B{

    @Override
    public void sameMethod() {
    }

    @Override
    public void defaultMethod() {

    }
}