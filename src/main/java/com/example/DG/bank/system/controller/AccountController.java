package com.example.DG.bank.system.controller;


import com.example.DG.bank.system.dto.AccountDtos.AccountRequestDTO;
import com.example.DG.bank.system.dto.AccountDtos.AccountRequestTRXSDto;
import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.service.AccountService;
import com.example.DG.bank.system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private CustomerService customerService;
    private AccountService accountService;
    @Autowired
    AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountRequestDTO>> getAllAccount() {
        List<AccountRequestDTO> accountDTOs = accountService.findAllAccountDTOs(Account::toAccountRequestDTO);
        return ResponseEntity.ok(accountDTOs);
    }

    @GetMapping("/all/Trxs")
    public ResponseEntity<List<AccountRequestTRXSDto>> getAccountsForTRXS() {
        List<AccountRequestTRXSDto> accountDTOs = accountService.findAllAccountDTOs(Account::toAccountDtoForTRXS);
        return ResponseEntity.ok(accountDTOs);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<AccountRequestDTO> getAccountById(@PathVariable("id") long id)
    {
        Account account = accountService.findAccountById(id);
        AccountRequestDTO accountRequestDTO = account.toAccountRequestDTO();
        return  new ResponseEntity<>(accountRequestDTO,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addAccount(@RequestBody AccountRequestDTO accountRequest )
    {
        System.out.println("arrived");

        Account newAccount = new Account();
        newAccount.setBalance(accountRequest.getBalance());
        newAccount.setType(accountRequest.getType());
        Customer customer =  customerService.findCustomerById(accountRequest.getCustomerid());
        newAccount.setCustomer(customer);
        accountService.addAccount(newAccount);
        Map<String,Object> response = new HashMap<>();
        if(customer != null)
        {
            response.put("message", "Account " + newAccount.getId() + " added successfully to Customer " + customer.getFirstName());
            response.put("account",newAccount);
            return  ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("message","Customer not Exsit in the System");
        return ResponseEntity.badRequest().body(response);
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
