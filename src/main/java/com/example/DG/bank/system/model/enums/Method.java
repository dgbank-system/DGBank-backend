package com.example.DG.bank.system.model.enums;

public enum Method {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    BANK_TRANSFER("Bank Transfer"),
    PAYPAL("PayPal"),
    OTHER("Other");

    private final String methodName;

    Method(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}