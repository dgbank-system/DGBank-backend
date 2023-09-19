package com.example.DG.bank.system.service;

import com.example.DG.bank.system.Repo.TransactionRepo;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.Transaction;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TransactonServiceImpl implements TransactionService{
    private TransactionRepo transactionRepo;

    @Autowired
    TransactonServiceImpl(TransactionRepo transactionRepo)
    {
        this.transactionRepo = transactionRepo;
    }
    @Override
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> FindAllTransaction() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    @Override
    public void deleteTransaction(long id) {
    transactionRepo.deleteById(id);
    }

    @Override
    public Transaction findTransactionById(long id) {
        return transactionRepo.findTransactionById(id).orElseThrow(() -> new UserNotFoundException("Transaction By Id " + id + "is not Found"));
    }

    @Override
    public Transaction updateTransactionbyId(long id, Transaction transaction) {
        Transaction existingTRAN = transactionRepo.findTransactionById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        existingTRAN.setMethod(transaction.getMethod());
        existingTRAN.setAmount(transaction.getAmount());
        existingTRAN.setDate(transaction.getDate());
        existingTRAN.setOperation(transaction.getOperation());
        existingTRAN.setDescription(transaction.getDescription());
        return transactionRepo.save(existingTRAN);
    }
}
