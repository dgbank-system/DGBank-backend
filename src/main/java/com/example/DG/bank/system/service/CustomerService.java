package com.example.DG.bank.system.service;

import com.example.DG.bank.system.dto.DepositRequest;
import com.example.DG.bank.system.dto.TransferRequest;
import com.example.DG.bank.system.model.Account;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Transaction;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    List<Customer> FindAllCustomer();

    Customer updateCustomer(Customer customer);

    void deleteCustomer(long id);

    Customer findCustomerById(long id);

    long findTotalCustomers();
//    Transaction tranfer(TransferRequest transferRequest);
//
//    Transaction deposite(DepositRequest depositRequest);
//
//    Transaction withdraw(DepositRequest depositRequest);

//    Customer getCustomerByAccountId(long id);
}
