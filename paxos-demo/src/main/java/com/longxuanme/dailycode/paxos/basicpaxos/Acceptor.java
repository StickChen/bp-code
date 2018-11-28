package com.longxuanme.dailycode.paxos.basicpaxos;

/**
 * @author Mloong
 * @date 2018/11/24
 */
public class Acceptor {

    private Integer promisedID;
    private Integer proposerID;
    private Integer acceptedID;
    private String acceptedValue;

    public Proposal handlePrepare(Proposal proposal) {
        // 模拟一定几率失败
        if (Math.random() - 0.4 < 0) {
            return null;
        }

        //
        if (this.promisedID == null || proposal.getId() > promisedID) {
            promisedID = proposal.getId();
            return new Proposal(acceptedID, acceptedValue, proposerID);
        }

        return null;
    }


    public Proposal handleAccept(Proposal proposal) {

        if (promisedID == null || proposal.getId() >= promisedID){
            promisedID = proposal.getId();
            acceptedID = proposal.getId();
            proposerID = proposal.getProposerId();
            acceptedValue = proposal.getValue();
            return proposal;
        }
        // promisedID > 提案id，拒绝，不回复

        return null;
    }
}
