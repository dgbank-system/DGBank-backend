package com.example.DG.bank.system.model.enums;

public enum Operation {
    Greater("Greater"),
    Less("Less"),
    Equal("Equal");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
