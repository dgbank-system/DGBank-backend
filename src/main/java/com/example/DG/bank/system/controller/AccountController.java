package com.example.DG.bank.system.controller;

import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin("*")
public class AccountController {

    private AccountService accountService;
    @Autowired
    AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccount()
    {
        List<Account> accounts = accountService.FindAllAccount();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") long id)
    {
        Account account = accountService.findAccountById(id);
        return  new ResponseEntity<>(account,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Account> addAccount(@RequestBody Account account)
    {
        Account newAccount = accountService.addAccount(account);
        return new ResponseEntity<>(newAccount,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateCustomer(@RequestBody Account account)
    {
        Account updatedaccount = accountService.updateAccount(account);
        return new ResponseEntity<>(updatedaccount,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") long id)
    {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
