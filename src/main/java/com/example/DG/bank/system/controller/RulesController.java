package com.example.DG.bank.system.controller;

import com.example.DG.bank.system.dto.Dates;
import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Rule;
import com.example.DG.bank.system.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rules")
@CrossOrigin("*")
public class RulesController {

    private final RuleService ruleService;

    @Autowired
    RulesController(RuleService ruleService)
    {
        this.ruleService = ruleService;
    }

    @PostMapping("/add")
    public ResponseEntity<Rule> addRule(@RequestBody Rule rule)
    {
       Rule newrule = ruleService.addRule(rule);
        return  new ResponseEntity<Rule>(newrule,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rule>> getAllRules()
    {
        List<Rule> rules = ruleService.FindAllRule();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<Rule> updateRule(@RequestBody Rule rule)
    {
        Rule updatedRule = ruleService.updateRule(rule);
        return new ResponseEntity<>(updatedRule,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable("id") long id)
    {
        ruleService.deleteRule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/applyRules")
    public ResponseEntity<?> applyRules()
    {
         ruleService.applyAllRulesOnTransactions();
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/selected-date")
    public ResponseEntity<Map<String, Long>> receiveSelectedDate(@RequestBody Dates selectedDate) {
        // Perform logic with the received selected date
        String dateType = selectedDate.getDateType();
        // Implement your logic here using the received dateType
        //call the transactionService.getTransactionCountsByDate(); and pass to it the dateType as parameter return the result from it
        Map<String,Long> res = ruleService.getAlertsCountsByDate(dateType);
        // You can return a response if needed
        return ResponseEntity.ok(res);
    }



}
