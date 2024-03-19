package com.example.DG.bank.system.service;

import com.example.DG.bank.system.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    List<Employee> FindAllEmployee();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(long id);

    Employee findEmployeeById(long id);
    Employee updateEmployeebyId(long id, Employee emp);

    Employee findEmployeeByUsername(String username);
    boolean register(String username);
   boolean login(String username , String password);

   long findTotalEmployees();
}
