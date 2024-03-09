package com.example.bank.service;

import lombok.RequiredArgsConstructor;
import com.example.bank.model.Account;
import org.springframework.stereotype.Service;
import com.example.bank.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Account updateAccountBalance(Long id, BigDecimal newBalance) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setBalance(newBalance);
            return accountRepository.save(account);
        } else {
            throw new NoSuchElementException("Account not found");
        }
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}