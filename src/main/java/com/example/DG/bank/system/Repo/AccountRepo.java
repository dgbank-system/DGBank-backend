package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {
    Optional<Account> findAccountById(long id);
}
