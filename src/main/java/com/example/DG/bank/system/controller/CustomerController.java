package com.example.DG.bank.system.controller;


import com.example.DG.bank.system.Repo.AlertRepo;

import com.example.DG.bank.system.dto.AlertProjection;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Transaction;
import com.example.DG.bank.system.service.CustomerService;
import com.example.DG.bank.system.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    private AlertRepo alertRepo;

    @Autowired
    private RuleService ruleService;

    @Autowired
    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers()
    {
        List<Customer> customers = customerService.FindAllCustomer();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id)
    {
        Customer customer = customerService.findCustomerById(id);
        return  new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addCustomer(@RequestBody Customer customer)
    {
        Customer newCustomer = customerService.addCustomer(customer);
        Map<String,Object> response = new HashMap<>();
        response.put("message","Customer " + newCustomer.getFirstName()+ " added in the system successfully and his id is" + newCustomer.getId());
        response.put("customer",newCustomer);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer)
    {
        Customer updatedcustomer = customerService.updateCustomer(customer);
        return new ResponseEntity<>(updatedcustomer,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id)
    {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    @PostMapping("/transfer")
//    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequest transferRequest)
//    {
//        Transaction trx=customerService.tranfer(transferRequest);
//        return new ResponseEntity<Transaction>(trx,HttpStatus.OK);
//    }
//
//    @PostMapping("/deposite")
//    public  ResponseEntity<Transaction> deposite(@RequestBody DepositRequest depositRequest)
//    {
//        Transaction trx = customerService.deposite(depositRequest);
//        return new ResponseEntity<Transaction>(trx,HttpStatus.OK);
//    }
//
//    @PostMapping("/withdraw")
//    public ResponseEntity<Transaction> withdraw(@RequestBody DepositRequest depositRequest)
//    {
//        Transaction trx = customerService.withdraw(depositRequest);
//        return new ResponseEntity<Transaction>(trx,HttpStatus.OK);
//    }

//    @GetMapping("/customer/{accountId}")
//    public ResponseEntity<Customer> getCustomerByAccountId(@PathVariable Long accountId) {
//       Customer customer = customerService.getCustomerByAccountId(accountId);
//
//
//            return ResponseEntity.ok(customer);
//
//            // Handle the case where the customer is not found
//
//    }


    @GetMapping("/transactions")
    public ResponseEntity<Map<Long, List<Transaction>>> getAllCustomerTransactions() {
        Map<Long, List<Transaction>> customerTransactionsMap = ruleService.getAllCustomerTransactions();
        return new ResponseEntity<>(customerTransactionsMap, HttpStatus.OK);
    }

    @GetMapping("alert")
    public ResponseEntity<List<AlertProjection>> getAlerts() {
        List<AlertProjection> alerts = alertRepo.findAllBy();
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }

    @GetMapping("/counts")
    public ResponseEntity<Long> getCustomersCount() {
        long customerCount = customerService.findTotalCustomers(); // Assuming you have a method in CustomerService to get the count
        return ResponseEntity.ok(customerCount);
    }


}
