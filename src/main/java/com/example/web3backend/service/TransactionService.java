package com.example.web3backend.service;

import com.example.web3backend.model.Transaction;
import com.example.web3backend.model.Stats;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class TransactionService {
    private List<Transaction> cachedTransactions = null;
    private final ObjectMapper mapper = new ObjectMapper();

    public void clearCache() {
        cachedTransactions = null;
    }

    public List<Transaction> getAllTransactions() throws Exception {
        if (cachedTransactions != null) {
            return cachedTransactions;
        }

        
        File file = new File("src/main/resources/data.json");

        cachedTransactions = Arrays.asList(
            mapper.readValue(file, Transaction[].class)
        );

        return cachedTransactions;
    }

    public Transaction getTransactionByHash(String hash) throws Exception {
        List<Transaction> transactions = getAllTransactions();

        for (Transaction tx : transactions) {
            if (tx.tx_hash.equals(hash)) {
                return tx;
            }
        }

        throw new RuntimeException("Transaction not found");
    }

    public List<Map.Entry<String, Double>> getTopSenders(Double minAmount, Double maxAmount) throws Exception {
        List<Transaction> transactions = getFilteredTransactions(minAmount, maxAmount);

        Map<String, Double> senders = new HashMap<>();

        for (Transaction tx : transactions) {
            senders.put(
                tx.from,
                senders.getOrDefault(tx.from, 0.0) + tx.amount
            );
        }

        List<Map.Entry<String, Double>> list = new ArrayList<>(senders.entrySet());

        list.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        return list.subList(0, Math.min(10, list.size()));
    }

    public Stats getStats(Double minAmount, Double maxAmount) throws Exception {
    List<Transaction> transactions = getFilteredTransactions(minAmount, maxAmount);

        double totalVolume = 0;
        Transaction largestTx = null;
        Map<String, Double> senders = new HashMap<>();

        for (Transaction tx : transactions) {
            totalVolume += tx.amount;

            if (largestTx == null || tx.amount > largestTx.amount) {
                largestTx = tx;
            }

            senders.put(
                tx.from,
                senders.getOrDefault(tx.from, 0.0) + tx.amount
            );
        }

        String topSender = null;
        double max = 0;

        for (String sender : senders.keySet()) {
            if (senders.get(sender) > max) {
                max = senders.get(sender);
                topSender = sender;
            }
        }

        return new Stats(totalVolume, largestTx, topSender);
    }

    public List<Transaction> getFilteredTransactions(Double minAmount, Double maxAmount) throws Exception {
        List<Transaction> transactions = getAllTransactions();

        List<Transaction> result = new ArrayList<>();

        for (Transaction tx : transactions) {

            boolean matches = true;

            if (minAmount != null && tx.amount < minAmount) {
                matches = false;
            }

            if (maxAmount != null && tx.amount > maxAmount) {
                matches = false;
            }

            if (matches) {
                result.add(tx);
            }
        }

        return result;
    }

    public List<Transaction> getTransactionsPaged(Double minAmount, Double maxAmount, int page, int size) throws Exception {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        size = Math.min(size, 100);

        List<Transaction> transactions = getFilteredTransactions(minAmount, maxAmount);

        int start = page * size;
        int end = Math.min(start + size, transactions.size());

        if (start >= transactions.size()) {
            return new ArrayList<>();
        }

        return transactions.subList(start, end);
    }
}