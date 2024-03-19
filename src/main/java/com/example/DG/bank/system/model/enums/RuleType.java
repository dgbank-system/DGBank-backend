package com.example.DG.bank.system.model.enums;



public enum RuleType {
    Customer("Customer"), // Represents rules based on a Customer
    Account("Account");

    private final String ruleType ;

    RuleType(String ruleType){
        this.ruleType = ruleType ;
    }

    public String getRuleType()
    {
        return this.ruleType;
    }// Represents rules based on an Account
}