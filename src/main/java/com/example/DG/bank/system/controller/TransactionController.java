package com.example.DG.bank.system.controller;

import com.example.DG.bank.system.model.Transaction;
import com.example.DG.bank.system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@PreAuthorize("hasRole(Admin)")

public class TransactionController {
    private TransactionService transactionService;
    @Autowired
    TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole()")
    public ResponseEntity<List<Transaction>> getAllTransaction()
    {
        List<Transaction> transactions = transactionService.FindAllTransaction();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") long id)
    {
        Transaction transaction = transactionService.findTransactionById(id);
        return  new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction)
    {
        Transaction newTransaction = transactionService.addTransaction(transaction);
        return new ResponseEntity<>(newTransaction,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction)
    {
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);
        return new ResponseEntity<>(updatedTransaction,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Transaction> updateTransactionById(@PathVariable("id") long id,@RequestBody Transaction transaction ){
        Transaction updatedTransaction = transactionService.updateTransactionbyId(id,transaction);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") long id)
    {
       transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
