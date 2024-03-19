package com.example.DG.bank.system.model;

import com.example.DG.bank.system.model.enums.Aggregation;
import com.example.DG.bank.system.model.enums.Operation;
import com.example.DG.bank.system.model.enums.RuleType;
import com.example.DG.bank.system.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rules")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Rule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;

    @Column(name = "transaction-type")
    private TransactionType transactionType;

    @Column(name = "rule-type")
    private RuleType ruleType;

    @Column(name = "operation")
    private Operation operation;

    @Column(name = "aggregation")
    private Aggregation aggregation;

    @Column(name="amount")
    private long amount;

    @Column(name="date")
    private LocalDate date;



}
