package com.example.DG.bank.system.controller;

import com.example.DG.bank.system.dto.LoginReguest;
import com.example.DG.bank.system.model.Employee;
import com.example.DG.bank.system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployee()
    {
        List<Employee> employees = employeeService.FindAllEmployee();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id)
    {
        Employee employee = employeeService.findEmployeeById(id);
        return  new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee employee)
    {

        if(!employeeService.register(employee.getUsername()))
       {
           Map<String, Object> response = new HashMap<>();
           response.put("message", "Username is already in use.");
           return ResponseEntity.badRequest().body(response);
       }
        Employee newEmployee = employeeService.addEmployee(employee);
        Map<String, Object> response = new HashMap<>();
        response.put("employee", newEmployee);
        response.put("message", "Register successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginReguest loginRequest) {
        String name = loginRequest.getUsername();
        String password = loginRequest.getPassword();

       Map<String,String> response = new HashMap<>();
        if (employeeService.login(name, password)) {
            Employee emp = employeeService.findEmployeeByUsername(name);
            response.put("emp",emp.getName());
            response.put("message","login successful");
            return ResponseEntity.ok(response);
        } else {
           response.put("message","login failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee)
    {
        Employee updatedemployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedemployee,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable("id") long id,@RequestBody Employee employee ){
        Employee updateEmployee = employeeService.updateEmployeebyId(id,employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/counts")
    public ResponseEntity<Long> getEmployeesCount() {
        long empCounts = employeeService.findTotalEmployees(); // Assuming you have a method in CustomerService to get the count
        return ResponseEntity.ok(empCounts);
    }

}
