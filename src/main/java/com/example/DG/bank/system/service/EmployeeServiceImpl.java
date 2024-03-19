package com.example.DG.bank.system.service;

import com.example.DG.bank.system.Repo.EmployeeRepo;
import com.example.DG.bank.system.exception.UserNotFoundException;
import com.example.DG.bank.system.model.Employee;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepo employeeRepo;

    @Autowired
    EmployeeServiceImpl(EmployeeRepo employeeRepo)
    {
        this.employeeRepo = employeeRepo;
    }
    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public List<Employee> FindAllEmployee() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepo.deleteById(id);

    }

    @Override
    public Employee findEmployeeById(long id) {
        return employeeRepo.findEmployeeById(id).orElseThrow(() -> new UserNotFoundException("Employee By Id  " + id +  "  is not Found"));
    }

    @Override
    public Employee updateEmployeebyId(long id, Employee emp) {
        Employee existingEmp = employeeRepo.findEmployeeById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        existingEmp.setName(emp.getName());
        existingEmp.setEmail(emp.getEmail());
        existingEmp.setPhone(emp.getPhone());
        existingEmp.setAddress(emp.getAddress());
        return employeeRepo.save(existingEmp);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        Employee emp = employeeRepo.findByUsername(username);
        return emp;
    }

    @Override
    public boolean register(String username) {
        Employee emp = employeeRepo.findByUsername(username);
        return emp == null;
    }


    public boolean login(String username, String password) {
        Employee employee = employeeRepo.findByUsername(username);

        if (employee != null) {
            // Check if the provided password matches the stored password
            return password.equals(employee.getPassword());
        }

        return false; // Employee not found
    }

    @Override
    public long findTotalEmployees() {
        return employeeRepo.count();
    }
}
