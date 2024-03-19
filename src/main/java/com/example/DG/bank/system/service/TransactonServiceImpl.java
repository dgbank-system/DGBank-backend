package com.example.DG.bank.system.service;
import java.util.*;

import com.example.DG.bank.system.Repo.RuleRepo;
import com.example.DG.bank.system.Repo.TransactionRepo;
import com.example.DG.bank.system.dto.DepositRequest;
import com.example.DG.bank.system.dto.TransactionDTO;
import com.example.DG.bank.system.dto.TransferRequest;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactonServiceImpl implements TransactionService{
    @Autowired
    private RuleService ruleService;

//    @Autowired
//    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    private TransactionRepo transactionRepo;

    @Autowired
    TransactonServiceImpl(TransactionRepo transactionRepo)
    {
        this.transactionRepo = transactionRepo;
    }
    @Override
    public Transaction addTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionRepo.save(transaction);


//        long customerId = transaction.getAccount1().getCustomer().getId();
       List<Rule>rules = ruleService.FindAllRule();
        for(Rule rule : rules)
        {

                ruleService.ExecuteRule(rule,transaction);
        }
//        ruleService.ExecuteRule(transaction);
//        ruleService.Rule1(customerId);
//
//        ruleService.Rule2(customerId);

        return savedTransaction;
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
        return transactionRepo.findTransactionById(id).orElseThrow(() -> new UserNotFoundException("Transaction By Id  " + id + " is  not Found"));
    }

    @Override
    public Transaction updateTransactionbyId(long id, Transaction transaction) {
        Transaction existingTRAN = transactionRepo.findTransactionById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
      //  existingTRAN.setMethod(transaction.getMethod());
        existingTRAN.setAmount(transaction.getAmount());
        existingTRAN.setDate(transaction.getDate());
       // existingTRAN.setOperation(transaction.getOperation());
        existingTRAN.setDescription(transaction.getDescription());
        return transactionRepo.save(existingTRAN);
    }

    @Override
    public Transaction tranfer(TransferRequest transferRequest) {
        //accountA
        LocalDate currentDate = LocalDate.now();
        String desc = null;
        String status = null;
        Transaction trx = new Transaction();
        Account account_A = accountService.findAccountById(transferRequest.getAccountA().getId());
        Account account_B = accountService.findAccountById(transferRequest.getAccountB().getId());

        if (account_A.getBalance() <= 0) {
            desc = "this transaction cannot be done as your balance is :  " + account_A.getBalance();
            status = "Failed";
        } else if (account_A.getBalance() < transferRequest.getAmount()) {
            desc = "this transaction cannot be done as your balance is :  " + account_A.getBalance();
            status = "Failed";
        } else if (account_B != null) {
            account_A.setBalance(account_A.getBalance() - transferRequest.getAmount());
            account_B.setBalance(account_B.getBalance() + transferRequest.getAmount());
            desc = "Transaction successfully completed from Account A (ID: " + account_A.getId() + ") to Account B (ID: " + account_B.getId() + ") by transferring an amount of " + transferRequest.getAmount() + ".";
            status = "Successful";
        } else if (account_B == null) {
            account_A.setBalance(account_A.getBalance() - transferRequest.getAmount());
            status = "Successful";
            desc = "Transaction successfully completed from Account A (ID: " + account_A.getId() + ") to Account B (ID: " + account_B.getId() + ") by transferring an amount of " + transferRequest.getAmount() + ".";
        }
        trx.setStatus(status);
        trx.setDescription(desc);
        trx.setAmount(transferRequest.getAmount());
        trx.setType("transfer");
        trx.setAccount1(account_A);
        trx.setAccount2(account_B);

        trx.setDate(currentDate);
        addTransaction(trx);


        return trx;
    }

    @Override
    public Transaction deposite(DepositRequest depositRequest) {
        LocalDate currentDate = LocalDate.now();
        String desc = null;
        String status  = null;
        Transaction trx = new Transaction();
        Account account_A = accountService.findAccountById(depositRequest.getAccountA().getId());
        if(account_A != null)
        {
            status = "Successful";
            account_A.setBalance(account_A.getBalance() + depositRequest.getAmount());

            desc = "Deposit successfully completed. Your funds have been added to your account.";
        }
        else{
            desc = "Account is not exist in system";
            status = "Failed";
        }
        trx.setStatus(status);
        trx.setDescription(desc);
        trx.setAmount(depositRequest.getAmount());
        trx.setType("deposite");
        trx.setAccount1(account_A);
        trx.setAccount2(null);
        trx.setDate(currentDate);
        addTransaction(trx);
        return trx;
    }



    @Override
    public Transaction withdraw(DepositRequest depositRequest) {
        LocalDate currentDate = LocalDate.now();
        String desc = null;
        String status = null;
        Transaction trx = new Transaction();
        Account account_A = accountService.findAccountById(depositRequest.getAccountA().getId());
        if(account_A != null)
        {
            if(account_A.getBalance() >= depositRequest.getAmount() )
            {
                status = "Successful";
                account_A.setBalance(account_A.getBalance() - depositRequest.getAmount());
                desc = "Withdrawal successfully processed. Your requested amount has been deducted from your account.";
            }
            else{
                status = "Failed";
                desc = "Insufficient funds. Your account balance is not sufficient to complete this withdrawal request. Please make sure you have enough funds in your account to proceed.";
            }

        }
        else{
            desc = "Account is not exist in system";
            status = "Failed";
        }
        trx.setStatus(status);
        trx.setDescription(desc);
        trx.setAmount(depositRequest.getAmount());
        trx.setType("withdraw");
        trx.setAccount1(account_A);
        trx.setAccount2(null);
        trx.setDate(currentDate);

         addTransaction(trx);
        return trx;
    }
    public Map<String, Long> getTransactionCountsByDate(String dateType) {
        List<Transaction> transactions = transactionRepo.findAll();
        Map<String, Long> transactionCount = new HashMap<>();
        if("Daily".equalsIgnoreCase(dateType))
        {
            System.out.println("iam in Daily :)");
            transactionCount  = transactions.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getDate().toString(),
                            Collectors.counting()
                    ));
        }
        else if("Monthly".equalsIgnoreCase(dateType))
        {
            System.out.println("iam in Monthly :)");
            transactionCount = transactions.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getDate().toString().substring(0, 7), // Extract year and month (e.g., "2023-10")
                            Collectors.counting()
                    ));
        }
        else if("Yearly".equalsIgnoreCase(dateType)){
            System.out.println("iam in Yearly :)");
            transactionCount = transactions.stream()
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getDate().toString().substring(0, 4), // Extract year and month (e.g., "2023-10")
                            Collectors.counting()
                    ));
        }

        Map<String, Long> sortedTransactionCounts = transactionCount.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));



        System.out.println(sortedTransactionCounts);


        return sortedTransactionCounts;
    }

    @Override
    public long findTotalTrxs() {
        return transactionRepo.count();
    }



}
