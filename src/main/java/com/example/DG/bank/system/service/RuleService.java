package com.example.DG.bank.system.service;

import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.Rule;
import com.example.DG.bank.system.model.Transaction;

import java.util.List;
import java.util.Map;

public interface RuleService {
    Rule addRule(Rule rule);

    List<Rule> FindAllRule();

    Rule updateRule(Rule rule);

    void deleteRule(long id);
    List<Transaction> findAllByCustomerId(long id);

    Map<Long, List<Transaction>> getAllCustomerTransactions();

    List<Transaction> getTransactionsByType(String type);




    Map<String, Long> getAlertsCountsByDate(String dateType);

    public void applyAllRulesOnTransactions();


}
