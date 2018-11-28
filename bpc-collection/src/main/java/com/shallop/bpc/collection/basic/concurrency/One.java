package com.shallop.bpc.collection.basic.concurrency;

import com.shallop.bpc.collection.basic.concurrency.volatile_demo.VolatileOoO2;

/**
 * @author StickChen
 * @date 2017/7/8
 */
public class One implements Runnable{
    public void run() {
        // 由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
        VolatileOoO2.shortWait(60000);
        VolatileOoO2.a = 1;
        VolatileOoO2.x = VolatileOoO2.b;  // 可重排序
    }

    public static void main(String[] args) {

    }
}
