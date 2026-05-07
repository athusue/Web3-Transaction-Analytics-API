package com.example.web3backend.model;

public class Stats {
    public double totalVolume;
    public Transaction largestTx;
    public String topSender;

    public Stats(double totalVolume, Transaction largestTx, String topSender) {
        this.totalVolume = totalVolume;
        this.largestTx = largestTx;
        this.topSender = topSender;
    }
}