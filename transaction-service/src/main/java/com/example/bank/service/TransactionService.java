package com.example.bank.service;

import com.example.bank.dto.TransactionDto;
import com.example.bank.model.TransactionType;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto dto);

    List<TransactionDto> getAllTransactions();

    Optional<TransactionDto> getTransactionById(Long id);

    List<TransactionDto> getTransactionsByAccountId(Long accountId);

    List<TransactionDto> getTransactionsByType(TransactionType type);

    List<TransactionDto> getTransactionsByAccountIdAndType(Long accountId, TransactionType type);

    void deleteTransaction(Long id);

    void deleteAllTransactions();
}
