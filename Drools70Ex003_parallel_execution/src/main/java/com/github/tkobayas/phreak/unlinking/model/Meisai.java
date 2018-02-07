package com.github.tkobayas.phreak.unlinking.model;

public class Meisai {

    private String id;

    private long amount;

    public Meisai() {

    }

    public Meisai(String id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}
