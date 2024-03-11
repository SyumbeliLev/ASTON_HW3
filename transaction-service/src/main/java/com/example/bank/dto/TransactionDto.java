package com.example.bank.dto;

import com.example.bank.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long accountId;
    private Long recipientAccountId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime timestamp;
}
