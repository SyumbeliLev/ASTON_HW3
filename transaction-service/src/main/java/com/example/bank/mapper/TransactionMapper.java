package com.example.bank.mapper;

import com.example.bank.dto.TransactionDto;
import com.example.bank.model.Transaction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {
    public TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setAccountId(transaction.getAccountId());
        dto.setRecipientAccountId(transaction.getRecipientAccountId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setTimestamp(transaction.getTimestamp());
        return dto;
    }

    public Transaction toEntity(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(dto.getAccountId());
        transaction.setRecipientAccountId(dto.getRecipientAccountId());
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setTimestamp(dto.getTimestamp());
        return transaction;
    }
}
