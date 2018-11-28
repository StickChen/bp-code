package com.longxuanme.dailycode.paxos;

import com.longxuanme.dailycode.paxos.basicpaxos.Acceptor;
import com.longxuanme.dailycode.paxos.basicpaxos.BasicPaxos;
import com.longxuanme.dailycode.paxos.basicpaxos.Proposer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final int NUM_OF_ACCEPTOR = 10;
    private static final int NUM_OF_PROPOSER = 5;

    @Test
    public void testAppTest() throws InterruptedException {

        List<Acceptor> acceptors = new ArrayList<>();
        for (int i = 0; i < NUM_OF_ACCEPTOR; i++) {
            acceptors.add(new Acceptor());
        }
        BasicPaxos.init(NUM_OF_PROPOSER);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < NUM_OF_PROPOSER; i++) {
            Proposer proposer = new Proposer(i, NUM_OF_PROPOSER, acceptors);
            threadPool.execute(proposer);
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
