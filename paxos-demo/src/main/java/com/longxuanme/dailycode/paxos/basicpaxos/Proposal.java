package com.longxuanme.dailycode.paxos.basicpaxos;

/**
 * @author StickChen
 * @date 2018/11/25
 */
public class Proposal {

    private Integer id;
    private String value;
    private Integer proposerId;

    public Proposal(Integer id, String value, Integer proposerId) {
        this.id = id;
        this.value = value;
        this.proposerId = proposerId;
    }

    public Integer getId() {
        return id;
    }

    public Proposal setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Proposal setValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getProposerId() {
        return proposerId;
    }

    public Proposal setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
        return this;
    }

    public Proposal copy(Proposal proposal) {
        this.setId(proposal.getId());
        this.setValue(proposal.getValue());
        return this;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", proposerId=" + proposerId +
                '}';
    }
}
