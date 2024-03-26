package com.example.DG.bank.system.service;


import com.example.DG.bank.system.dto.AccountDtos.AccountRequestDTO;
import com.example.DG.bank.system.model.Account;

import java.util.List;
import java.util.function.Function;

public interface AccountService {
    Account addAccount(Account account);

    <T> List<T> findAllAccountDTOs(Function<Account, T> mapper);



    Account updateAccount(Account account);

    void deleteAccount(long id);

    Account findAccountById(long id);
    Boolean findAccount(long id);

    List<Long> findAllAccountIds();

}
