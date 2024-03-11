package com.example.bank.service;

import com.example.bank.dto.TransactionDto;
import com.example.bank.mapper.TransactionMapper;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.toEntity(transactionDto);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.toDto(savedTransaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream().map(TransactionMapper::toDto).toList();
    }

    @Override
    public Optional<TransactionDto> getTransactionById(Long id) {
        return transactionRepository.findById(id).map(TransactionMapper::toDto);
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId).stream().map(TransactionMapper::toDto).toList();
    }

    @Override
    public List<TransactionDto> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type).stream().map(TransactionMapper::toDto).toList();
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountIdAndType(Long accountId, TransactionType type) {
        return transactionRepository.findByAccountIdAndType(accountId, type).stream().map(TransactionMapper::toDto).toList();
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }

    @KafkaListener(topics = "account-transaction", groupId = "my-group")
    public void listen(String message) {
        String[] parts = message.split(" ");
        Transaction transaction = new Transaction();
        long accountId;
        BigDecimal amount;
        if (message.contains("Transfer")) {
            long fromAccountId = Long.parseLong(parts[4]);
            Long toAccountId = Long.parseLong(parts[8]);
            amount = new BigDecimal(parts[11]);
            transaction.setRecipientAccountId(toAccountId);
            accountId = fromAccountId;
            transaction.setType(TransactionType.TRANSFER);
        } else {
            accountId = Long.parseLong(parts[4]);
            amount = new BigDecimal(parts[7]);
            transaction.setType(TransactionType.WITHDRAWAL);
        }
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}

