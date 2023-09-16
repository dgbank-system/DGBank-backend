package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerById(long id);

}
