package com.longxuanme.dailycode


abstract class Language

interface JVMRunnable{
    fun runOnJVM()
}

class DefaultJVMRunnable : JVMRunnable {
    override fun runOnJVM() {
        println("running on JVM!")
    }
}

class Java(jvmRunnable: JVMRunnable) : Language(), JVMRunnable by jvmRunnable
class Kotlin(jvmRunnable: JVMRunnable) : Language(), JVMRunnable by jvmRunnable{
    fun runOnFE(){

    }
}

open class A {
    open fun f() { print("A") }
    fun a() { print("a") }
}

interface B {
    fun f() { print("B") } // 接口成员默认就是“open”的
    fun b() { print("b") }
}

class C() : A(), B {
    // 编译器要求覆盖 f()：
    override fun f() {
        super<A>.f() // 调用 A.f()
        super<B>.f() // 调用 B.f()
    }
}
