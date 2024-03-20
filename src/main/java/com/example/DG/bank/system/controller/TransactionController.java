package com.example.DG.bank.system.controller;

import com.example.DG.bank.system.dto.*;
import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Transaction;
import com.example.DG.bank.system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
@CrossOrigin("*")
public class TransactionController {
    private TransactionService transactionService;
    @Autowired
    TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> getAllTransaction()
    {
        List<Transaction> transactions = transactionService.FindAllTransaction();
        List<TransactionDTO> trxDTOs = transactions.stream()
                .map(Transaction::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(trxDTOs, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") long id)
    {
        Transaction transaction = transactionService.findTransactionById(id);
        return  new ResponseEntity<>(transaction,HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction)
//    {
//        Transaction newTransaction = transactionService.addTransaction(transaction);
//        return new ResponseEntity<>(newTransaction,HttpStatus.CREATED);
//    }

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
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transfer(@RequestBody TransferRequest transferRequest)
    {
        Transaction trx= transactionService.tranfer(transferRequest);
        TransactionDTO transactionDTO = trx.toDTO();
        return new ResponseEntity<TransactionDTO>(transactionDTO,HttpStatus.OK);
    }

    @PostMapping("/deposite")
    public  ResponseEntity<TransactionDTO> deposite(@RequestBody DepositRequest depositRequest)
    {
        Transaction trx = transactionService.deposite(depositRequest);
        TransactionDTO transactionDTO = trx.toDTO();
        return new ResponseEntity<TransactionDTO>(transactionDTO,HttpStatus.OK);

    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(Long id , Long amount) {
        Transaction res = transactionService.withdraw(id, amount);

        TransactionDTO transactionDTO = res.toDTO();

        return new ResponseEntity<TransactionDTO>(transactionDTO,HttpStatus.CREATED);
    }

//    @GetMapping("/transactionCountsByDate")
//    public Map<String, Long> getTransactionCountsByDate() {
//        return transactionService.getTransactionCountsByDate();
//
//    }
    @PostMapping("/selected-date")
    public ResponseEntity<Map<String, Long>> receiveSelectedDate(@RequestBody Dates selectedDate) {
        // Perform logic with the received selected date
        String dateType = selectedDate.getDateType();
        // Implement your logic here using the received dateType
        //call the transactionService.getTransactionCountsByDate(); and pass to it the dateType as parameter return the result from it
       Map<String,Long> res = transactionService.getTransactionCountsByDate(dateType);
        // You can return a response if needed
        return ResponseEntity.ok(res);
    }


    @GetMapping("/counts")
    public ResponseEntity<Long> getTransactionsCount() {
        long trxsCounts = transactionService.findTotalTrxs(); // Assuming you have a method in CustomerService to get the count
        return ResponseEntity.ok(trxsCounts);
    }

}
