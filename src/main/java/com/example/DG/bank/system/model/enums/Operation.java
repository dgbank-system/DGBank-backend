package com.example.DG.bank.system.model.enums;

public enum Operation {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer"),
    PAYMENT("Payment"),
    OTHER("Other");

    private final String operationName;

    Operation(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }
}
