package com.example.DG.bank.system.service;

import com.example.DG.bank.system.dto.AccountRequestDTO;
import com.example.DG.bank.system.model.Account;

import java.util.List;

public interface AccountService {
    Account addAccount(Account account);

    List<AccountRequestDTO> findAllAccountDTOs();

    Account updateAccount(Account account);

    void deleteAccount(long id);

    Account findAccountById(long id);
    Boolean findAccount(long id);

}
