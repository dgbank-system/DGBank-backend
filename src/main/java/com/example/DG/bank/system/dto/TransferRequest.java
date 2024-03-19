package com.example.DG.bank.system.dto;

import com.example.DG.bank.system.model.Account;
import lombok.Data;

@Data
public class TransferRequest {
    private Account  accountA;
    private Account accountB;
    private long amount;

    // Getters and setters
}