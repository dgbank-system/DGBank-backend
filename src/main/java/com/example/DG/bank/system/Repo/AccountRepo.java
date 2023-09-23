package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {
    Optional<Account> findAccountById(long id);

}
