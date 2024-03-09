package com.example.bank.service;

import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();

    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);

    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);

    }

    @Override
    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type);

    }

    @Override
    public List<Transaction> getTransactionsByAccountIdAndType(Long accountId, TransactionType type) {
        return transactionRepository.findByAccountIdAndType(accountId, type);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);

    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();

    }
}

