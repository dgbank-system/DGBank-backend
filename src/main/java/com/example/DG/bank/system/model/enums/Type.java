package com.example.DG.bank.system.model.enums;

public enum Type {
    SAVINGS("Savings"),
    CHECKING("Checking");

    private final String accountType;

    Type(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return this.accountType;
    }
}

