package com.example.bank.controller;

import com.example.bank.dto.TransactionDto;
import com.example.bank.model.TransactionType;
import com.example.bank.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@Api(value = "Transaction Management System", description = "Operations pertaining to transactions in Transaction Management System")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new transaction")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto dto) {
        log.info("Received request to create new transaction: {}", dto);
        TransactionDto newTransaction = transactionService.createTransaction(dto);
        log.info("New transaction created: {}", newTransaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get a list of all transactions")
    public List<TransactionDto> getAllTransactions() {
        log.info("Received request to get all transactions");
        List<TransactionDto> transactions = transactionService.getAllTransactions();
        log.info("Returned {} transactions", transactions.size());
        return transactions;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a transaction by Id")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
        log.info("Received request to get transaction by Id: {}", id);
        Optional<TransactionDto> transaction = transactionService.getTransactionById(id);
        return transaction.map(value -> {
            log.info("Transaction found: {}", value);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Transaction with Id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping("/account/{accountId}")
    @ApiOperation(value = "Get transactions by account Id")
    public List<TransactionDto> getTransactionsByAccountId(@PathVariable Long accountId) {
        log.info("Received request to get transactions by account Id: {}", accountId);
        List<TransactionDto> transactions = transactionService.getTransactionsByAccountId(accountId);
        log.info("Returned {} transactions", transactions.size());
        return transactions;
    }

    @GetMapping("/type/{type}")
    @ApiOperation(value = "Get transactions by type")
    public List<TransactionDto> getTransactionsByType(@PathVariable String type) {
        log.info("Received request to get transactions by type: {}", type);
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        List<TransactionDto> transactions = transactionService.getTransactionsByType(transactionType);
        log.info("Returned {} transactions", transactions.size());
        return transactions;
    }

    @GetMapping("/account/{accountId}/type/{type}")
    @ApiOperation(value = "Get transactions by account Id and type")
    public List<TransactionDto> getTransactionsByAccountIdAndType(@PathVariable Long accountId, @PathVariable String type) {
        log.info("Received request to get transactions by account Id {} and type {}", accountId, type);
        TransactionType transactionType = TransactionType.valueOf(type.toUpperCase());
        List<TransactionDto> transactions = transactionService.getTransactionsByAccountIdAndType(accountId, transactionType);
        log.info("Returned {} transactions", transactions.size());
        return transactions;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a transaction by Id")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.info("Received request to delete transaction by Id: {}", id);
        transactionService.deleteTransaction(id);
        log.info("Transaction deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete all transactions")
    public ResponseEntity<Void> deleteAllTransactions() {
        log.info("Received request to delete all transactions");
        transactionService.deleteAllTransactions();
        log.info("All transactions deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
