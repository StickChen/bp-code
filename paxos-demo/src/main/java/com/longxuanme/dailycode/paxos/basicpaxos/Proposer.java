package com.longxuanme.dailycode.paxos.basicpaxos;

import java.util.List;

import static com.longxuanme.dailycode.paxos.basicpaxos.BasicPaxos.pt;

/**
 * @author Mloong
 * @date 2018/11/24
 */
public class Proposer implements Runnable {

    private int proposerId;
    private String name;
    private int quorumSize;
    private int proposerSize;
    private int cycleCount;
    private List<Acceptor> acceptors;

    public Proposer(int proposerId, int proposerSize, List<Acceptor> acceptors) {
        this.proposerId = proposerId;
        this.name = "#Proposer " + proposerId;
        this.proposerSize = proposerSize;
        this.acceptors = acceptors;

        quorumSize = acceptors.size() / 2 + 1;

    }

    @Override
    public void run() {
        BasicPaxos.startLatch.countDown();

        try {
            BasicPaxos.startLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            int proposalId = BasicPaxos.generateId(proposerId, cycleCount, proposerSize);
            String value = "master is " + proposerId;
            Proposal proposal = new Proposal(proposalId, value, proposerId);
            pt(name + " prepare cycleCount " + cycleCount + " " + proposal);
            if (prepare(proposal)) {
                pt(name + " commit cycleCount "  + cycleCount + " " + proposal);
                if (commit(proposal)) {
                    break;
                }
            }
            cycleCount++;
        }
    }

    private boolean prepare(Proposal proposal) {

        // 发起提案
        int receivedCount = 0;
        Integer maxProposalId = null;
        String maxProposalValue = null;
        for (Acceptor acceptor : acceptors) {

            Proposal prepareProposal = acceptor.handlePrepare(proposal);

            BasicPaxos.sleepRandom();
            if (prepareProposal == null) {
                continue;
            }
            receivedCount++;
            if (maxProposalId == null || (prepareProposal.getId() != null && prepareProposal.getId() > maxProposalId)) {
                maxProposalId = prepareProposal.getId();
                if (prepareProposal.getValue() != null) {
                    maxProposalValue = prepareProposal.getValue();
                }
            }
        }

        // 超过半数响应
        if (receivedCount >= quorumSize) {
            String proposalValue = maxProposalValue != null ? maxProposalValue : proposal.getValue();
            proposal.setValue(proposalValue);
            return true;
        }

        // 可监测acceptor是否已经达成决议，需修改返回结果


        return false;
    }

    private boolean commit(Proposal proposal) {
        int receivedCount = 0;
        for (Acceptor acceptor : acceptors) {

            Proposal acceptProposal = acceptor.handleAccept(proposal);
            if (acceptProposal != null) {
                receivedCount++;
            }

        }

        if (receivedCount >= quorumSize) {
            pt("Complete cycleCount " + cycleCount + " " + name + " " + proposal);
            return true;
        }
        // 不能使用通过的提案中序列号最大的决策
        // 可监测acceptor是否已经达成决议，需修改返回结果

        return false;
    }

    @Override
    public String toString() {
        return "Proposer{" +
                "proposerId=" + proposerId +
                ", name='" + name + '\'' +
                ", quorumSize=" + quorumSize +
                ", proposerSize=" + proposerSize +
                ", cycleCount=" + cycleCount +
                '}';
    }
}
