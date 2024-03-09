package com.example.bank.repository;

import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByAccountIdAndType(Long accountId, TransactionType type);
}