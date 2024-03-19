package com.example.DG.bank.system.dto;

import com.example.DG.bank.system.model.Account;
import lombok.Data;

@Data
public class DepositRequest {
    private Account accountA;
    private long amount;
}
