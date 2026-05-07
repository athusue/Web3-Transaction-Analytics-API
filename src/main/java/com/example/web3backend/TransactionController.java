package com.example.web3backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web3backend.model.Transaction;
import com.example.web3backend.service.TransactionService;
import com.example.web3backend.model.Stats;
import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/cache/clear")
    public String reload() {
        transactionService.clearCache();
        return "cache cleared";
    }

    

    @GetMapping("/top-senders")
    public List<Map.Entry<String, Double>> getTopSenders(
        @RequestParam(required = false) Double minAmount,
        @RequestParam(required = false) Double maxAmount
    ) throws Exception {
        return transactionService.getTopSenders(minAmount, maxAmount);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(
        @RequestParam(required = false) Double minAmount,
        @RequestParam(required = false) Double maxAmount,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) throws Exception {

        return transactionService.getTransactionsPaged(minAmount, maxAmount, page, size);
    }

    @GetMapping("/transactions/{hash}")
    public Transaction getTransactionByHash(
        @PathVariable String hash
    ) throws Exception {
        return transactionService.getTransactionByHash(hash);
    }

    @GetMapping("/stats")
    public Stats getStats(
        @RequestParam(required = false) Double minAmount,
        @RequestParam(required = false) Double maxAmount
    ) throws Exception {
        return transactionService.getStats(minAmount, maxAmount);
    }   

}