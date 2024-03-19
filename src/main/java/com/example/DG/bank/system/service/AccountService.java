package com.example.DG.bank.system.service;

import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;

import java.util.List;

public interface AccountService {
    Account addAccount(Account account);

    List<Account> FindAllAccount();

    Account updateAccount(Account account);

    void deleteAccount(long id);

    Account findAccountById(long id);
    Boolean findAccount(long id);

}
