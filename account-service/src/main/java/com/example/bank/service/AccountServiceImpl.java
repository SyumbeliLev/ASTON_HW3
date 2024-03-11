package com.example.bank.service;

import com.example.bank.dto.AccountDto;
import com.example.bank.feign.FeignNotification;
import com.example.bank.mapper.AccountMapper;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final FeignNotification feignNotification;
    private final KafkaTemplate<String, String> kafkaProducer;

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        if (accountDto.getBalance() == null) {
            accountDto.setBalance(BigDecimal.ZERO);
        }
        Account account = accountRepository.save(AccountMapper.toEntity(accountDto));
        feignNotification.createNotification("New account created with ID: " + account.getId());
        return AccountMapper.toDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDto> getAccountById(Long id) {
        return accountRepository.findById(id).map(AccountMapper::toDto);
    }

    @Override
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new NoSuchElementException("From Account not found"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new NoSuchElementException("To Account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in the from account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        feignNotification.createNotification("Transfer from account ID: " + fromAccountId + " to account ID: " + toAccountId + " with amount: " + amount);
        kafkaProducer.send("account-transaction", "Transfer from account ID: " + fromAccountId + " to account ID: " + toAccountId + " with amount: " + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Override
    @Transactional
    public void withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in the account");
        }

        account.setBalance(account.getBalance().subtract(amount));
        feignNotification.createNotification("Withdrawal from account ID: " + accountId + " with amount: " + amount);
        kafkaProducer.send("account-transaction", "Withdrawal from account ID: " + accountId + " with amount: " + amount);

        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountDto> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).map(AccountMapper::toDto);
    }

    @Override
    @Transactional
    public AccountDto updateAccountBalance(Long id, BigDecimal newBalance) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found"));

        account.setBalance(newBalance);
        feignNotification.createNotification("Account balance updated for account ID: " + id);

        return AccountMapper.toDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
        feignNotification.createNotification("Account deleted with ID: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(AccountMapper::toDto).toList();
    }
}