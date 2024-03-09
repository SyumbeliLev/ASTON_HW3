package com.example.bank.service;

import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    Optional<Transaction> getTransactionById(Long id);

    List<Transaction> getTransactionsByAccountId(Long accountId);

    List<Transaction> getTransactionsByType(TransactionType type);

    List<Transaction> getTransactionsByAccountIdAndType(Long accountId, TransactionType type);

    void deleteTransaction(Long id);

    void deleteAllTransactions();
}
