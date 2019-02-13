package com.shallop.bpc.collection.basic.concurrency.sychronized;

/**
 * @author chenxuanlong
 * @date 2019/1/24
 */
public class SyncDemo {

    public static void main(String[] args) {

        SyncDemo syncDemo1 = new SyncDemo();
        syncDemo1.startThreadA();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        syncDemo1.startThreadB();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        syncDemo1.startThreadC();


    }

    final Object lock = new Object();


    public void startThreadA() {
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("A get lock");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A release lock");
            }
        }, "thread-A").start();
    }

    public void startThreadB() {
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("B get lock");
            }
        }, "thread-B").start();
    }

    public void startThreadC() {
        new Thread(() -> {
            synchronized (lock) {

                System.out.println("C get lock");
            }
        }, "thread-C").start();
    }


}
