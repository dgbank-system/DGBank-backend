package com.example.DG.bank.system.dto;

import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Transaction;
import lombok.Data;

import java.util.List;
@Data
public class CustomerDto {
    private Customer customer;
    private List<Transaction> transactions;

    public CustomerDto(Customer customer, List<Transaction> transactions) {
        this.customer = customer;
        this.transactions = transactions;
    }
}
