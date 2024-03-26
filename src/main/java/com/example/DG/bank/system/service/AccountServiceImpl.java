package com.example.DG.bank.system.service;

import com.example.DG.bank.system.Repo.AccountRepo;

import com.example.DG.bank.system.dto.AccountDtos.AccountRequestDTO;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{


    private AccountRepo accountRepo;
    @Autowired
    AccountServiceImpl(AccountRepo accountRepo)
    {
        this.accountRepo = accountRepo;
    }
    @Override
    public Account addAccount(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public <T> List<T> findAllAccountDTOs(Function<Account, T> mapper) {
        List<Account> accounts = accountRepo.findAll();
        return accounts.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public void deleteAccount(long id) {
    accountRepo.deleteById(id);
    }

    @Override
    public Account findAccountById(long id) {
        return accountRepo.findAccountById(id).orElseThrow(() -> new UserNotFoundException("Account By Id  " + id +  " is not Found"));
    }

    @Override
    public Boolean findAccount(long id) {
        Account a = findAccountById(id);
        return a != null;
    }

    @Override
    public List<Long> findAllAccountIds() {
        return accountRepo.findAllIds();
    }


}
