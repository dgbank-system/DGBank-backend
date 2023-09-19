package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    Optional<Transaction> findTransactionById(long id);
}
