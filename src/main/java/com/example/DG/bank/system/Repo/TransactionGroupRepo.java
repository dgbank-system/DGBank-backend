package com.example.DG.bank.system.Repo;


import com.example.DG.bank.system.model.TransactionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionGroupRepo extends JpaRepository<TransactionGroup,Long> {
}
