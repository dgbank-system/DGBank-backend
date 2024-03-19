package com.example.DG.bank.system.dto;

import com.example.DG.bank.system.model.Transaction;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlertDTO {
    private long id;
    private String customerFirstName;
    private String customerLastName;
    private LocalDate date;
    private Long accountId;
    private String description;
    private long rule ;
    private List<Transaction> trxs;

    // Constructors, getters, and setters
}

