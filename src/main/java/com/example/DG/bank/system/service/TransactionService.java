package com.example.DG.bank.system.service;
import com.example.DG.bank.system.model.Transaction;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> FindAllTransaction();

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(long id);

    Transaction findTransactionById(long id);
   Transaction updateTransactionbyId(long id, Transaction transaction);
    Transaction tranfer(Long id1 ,Long id2 , Long amount);

    Transaction deposite(Long id , Long amount);

    Transaction withdraw(Long id , Long amount);

    Map<String, Long> getTransactionCountsByDate(String dateType);

    long findTotalTrxs();



}
