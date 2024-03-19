package com.example.DG.bank.system.model.enums;

public enum TransactionType {

    Transfer("Transfer") ,
    Withdraw("Withdraw"),

    Deposite("Deposite");

    private final String trxType ;

    TransactionType(String trxType){
        this.trxType = trxType ;
    }

    public String getTrxType()
    {
        return this.trxType;
    }

}
