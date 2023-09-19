package com.example.DG.bank.system.service;

import com.example.DG.bank.system.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> FindAllTransaction();

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(long id);

    Transaction findTransactionById(long id);
    Transaction updateTransactionbyId(long id, Transaction transaction);
}
