package com.longxuanme.dailycode.paxos.basicpaxos;

import java.util.concurrent.CountDownLatch;

/**
 * @author StickChen
 * @date 2018/11/25
 */
public class BasicPaxos {

    public static CountDownLatch startLatch;

    public static void init(int proposerNum) {
        startLatch = new CountDownLatch(proposerNum);
    }

    public static int generateId(int baseId, int cycleCount, int proposerSize) {
        return cycleCount * proposerSize + baseId;
    }

    public static void sleepRandom() {
        // TODO Auto-generated method stub

    }

    public static void pt(Object obj) {
        System.out.println(obj);
    }
}
