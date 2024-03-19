package com.example.DG.bank.system.service;

import com.example.DG.bank.system.dto.DepositRequest;
import com.example.DG.bank.system.dto.TransactionDTO;
import com.example.DG.bank.system.dto.TransferRequest;
import com.example.DG.bank.system.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> FindAllTransaction();

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(long id);

    Transaction findTransactionById(long id);
   Transaction updateTransactionbyId(long id, Transaction transaction);
    Transaction tranfer(TransferRequest transferRequest);

    Transaction deposite(DepositRequest depositRequest);

    Transaction withdraw(DepositRequest depositRequest);

    Map<String, Long> getTransactionCountsByDate(String dateType);

    long findTotalTrxs();



}
