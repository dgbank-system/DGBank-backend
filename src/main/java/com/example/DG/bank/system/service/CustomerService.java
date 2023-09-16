package com.example.DG.bank.system.service;

import com.example.DG.bank.system.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    List<Customer> FindAllCustomer();

    Customer updateCustomer(Customer customer);

    void deleteCustomer(long id);

    Customer findCustomerById(long id);
}
