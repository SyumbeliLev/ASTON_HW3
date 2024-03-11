package com.example.bank.controller;


import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Api(value = "Account Management System", description = "Operations pertaining to accounts in Account Management System")
public class AccountController {
    private final AccountService accountService;
    @PostMapping
    @ApiOperation(value = "Create a new account")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto account) {
        AccountDto createdAccount = accountService.createAccount(account);
        log.info("Account created successfully: {}", createdAccount);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an account by Id")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id).map(account -> {
            log.info("Account found: {}", account);
            return ResponseEntity.ok(account);
        }).orElseGet(() -> {
            log.warn("Account with Id {} not found", id);
            return ResponseEntity.notFound().build();
        });
    }

    @GetMapping("/number/{accountNumber}")
    @ApiOperation(value = "Get an account by account number")
    public ResponseEntity<AccountDto> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber).map(account -> {
            log.info("Account found: {}", account);
            return ResponseEntity.ok(account);
        }).orElseGet(() -> {
            log.warn("Account with number {} not found", accountNumber);
            return ResponseEntity.notFound().build();
        });
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update account balance")
    public ResponseEntity<AccountDto> updateAccountBalance(@PathVariable Long id, @RequestParam("balance") BigDecimal balance) {
        AccountDto updatedAccount = accountService.updateAccountBalance(id, balance);
        log.info("Account balance updated successfully: {}", updatedAccount);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an account by Id")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Get a list of all accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        log.info("Received request to get all accounts");
        List<AccountDto> accounts = accountService.getAllAccounts();
        log.info("Returned {} accounts", accounts.size());
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/transfer")
    @ApiOperation(value = "Transfer amount between accounts")
    public ResponseEntity<String> transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam BigDecimal amount) {
        log.info("Received request to transfer amount from account {} to account {}: amount = {}", fromAccountId, toAccountId, amount);
        accountService.transfer(fromAccountId, toAccountId, amount);
        log.info("Amount transferred successfully");
        return ResponseEntity.ok("Transfer successful");
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "Withdraw amount from an account")
    public ResponseEntity<String> withdraw(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        log.info("Received request to withdraw amount from account {}: amount = {}", accountId, amount);
        accountService.withdraw(accountId, amount);
        log.info("Amount withdrawn successfully");
        return ResponseEntity.ok("Withdrawal successful");
    }
}
