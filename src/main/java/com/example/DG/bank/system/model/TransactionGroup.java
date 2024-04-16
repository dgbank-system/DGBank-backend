package com.example.DG.bank.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TransactionGroup")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope; // "customer" or "account"

    private Long customerId; // customer ID

    private Long accountId; // account ID

    private long aggregatedAmount; //result

    private String transactionType ;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "transactionGroups")
    private List<Alert> alerts= new ArrayList<>();
    public TransactionGroup(String scope, Long customerId, Long accountId, long aggregatedAmount,String transactionType) {
        this.scope = scope;
        this.customerId = customerId;
        this.accountId = accountId;
        this.aggregatedAmount = aggregatedAmount;
        this.transactionType = transactionType;
    }
}
