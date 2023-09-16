package com.example.DG.bank.system.service;

import com.example.DG.bank.system.Repo.CustomerRepo;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepo customerRepo;

    @Autowired
    CustomerServiceImpl(CustomerRepo customerRepo)
    {
        this.customerRepo = customerRepo;
    }
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> FindAllCustomer() {
        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public Customer findCustomerById(long id) {
        return customerRepo.findCustomerById(id).orElseThrow(() -> new UserNotFoundException("Customer By Id " + id + "is not Found"));
    }
}
