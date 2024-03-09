package com.example.bank.controller;

import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

    @GetMapping("/type/{type}")
    public List<Transaction> getTransactionsByType(@PathVariable String type) {
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        return transactionService.getTransactionsByType(transactionType);
    }

    @GetMapping("/account/{accountId}/type/{type}")
    public List<Transaction> getTransactionsByAccountIdAndType(@PathVariable Long accountId, @PathVariable String type) {
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        return transactionService.getTransactionsByAccountIdAndType(accountId, transactionType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactions() {
        transactionService.deleteAllTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}