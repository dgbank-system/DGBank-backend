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
    private Long accountId;
    private Long anotherAccountId;
    private List<Alert> alerts;
    private Double balance;
    private Map<String, Integer> transactionCounts;
    // Constructors, getters, and setters
}


