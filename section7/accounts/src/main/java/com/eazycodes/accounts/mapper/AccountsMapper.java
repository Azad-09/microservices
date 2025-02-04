package com.eazycodes.accounts.mapper;

import com.eazycodes.accounts.dto.AccountsDto;
import com.eazycodes.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsdto(AccountsDto accountsDto, Accounts accounts){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setAddress(accounts.getAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(Accounts accounts, AccountsDto accountsDto){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setAddress(accountsDto.getAddress());
        return accounts;
    }
}
