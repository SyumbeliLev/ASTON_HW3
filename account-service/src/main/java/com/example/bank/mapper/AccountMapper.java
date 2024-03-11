package com.example.bank.mapper;

import com.example.bank.dto.AccountDto;
import com.example.bank.model.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getAccountNumber(), account.getBalance());
    }

    public Account toEntity(AccountDto accountDto) {
        return new Account(0l, accountDto.getAccountNumber(), accountDto.getBalance());
    }
}
