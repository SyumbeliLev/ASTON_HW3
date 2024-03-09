package com.example.bank.service;

import com.example.bank.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account);

    Optional<Account> getAccountById(Long id);

    Optional<Account> getAccountByNumber(String accountNumber);

    Account updateAccountBalance(Long id, BigDecimal newBalance);

    void deleteAccount(Long id);

    List<Account> getAllAccounts();
}