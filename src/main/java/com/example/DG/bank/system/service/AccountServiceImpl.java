package com.example.DG.bank.system.service;

import com.example.DG.bank.system.Repo.AccountRepo;
import com.example.DG.bank.system.dto.AccountRequestDTO;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<AccountRequestDTO> findAllAccountDTOs() {
        List<Account> accounts = accountRepo.findAll();
        return accounts.stream()
                .map(Account::toDTO)
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
        if(a != null) return true;
        return false;
    }


}
