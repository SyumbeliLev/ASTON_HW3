package com.example.bank.service;

import com.example.bank.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountDto createAccount(AccountDto account);

    Optional<AccountDto> getAccountById(Long id);

    void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount);

    void withdraw(Long accountId, BigDecimal amount);

    Optional<AccountDto> getAccountByNumber(String accountNumber);

    AccountDto updateAccountBalance(Long id, BigDecimal newBalance);

    void deleteAccount(Long id);

    List<AccountDto> getAllAccounts();
}