package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.entity.Account;

public class AccountMapper {

  private AccountMapper() {
  }

  public static AccountDTO mapToAccountsDTO(Account account, AccountDTO accountDTO) {
    accountDTO.setAccountNumber(account.getAccountNumber());
    accountDTO.setAccountType(account.getAccountType());
    accountDTO.setBranchAddress(account.getBranchAddress());
    return accountDTO;
  }

  public static Account mapToAccounts(AccountDTO accountDTO, Account account) {
    account.setAccountNumber(accountDTO.getAccountNumber());
    account.setAccountType(accountDTO.getAccountType());
    account.setBranchAddress(accountDTO.getBranchAddress());
    return account;
  }
}
