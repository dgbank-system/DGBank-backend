package com.example.DG.bank.system.dto;
import com.example.DG.bank.system.model.Alert;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Data
public class TransactionDTO {
    private long id;
    private String type;
    private long amount;
    private LocalDate date;
    private String status;
    private String description;
    private Long accountId; // Represents the account_id
    private Long anotherAccountId; // Represents the another_account field
    private List<Alert> alerts;
    private Map<String, Integer> transactionCounts;
    // Constructors, getters, and setters
}


