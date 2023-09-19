package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Optional<Employee> findEmployeeById(long id);
}
