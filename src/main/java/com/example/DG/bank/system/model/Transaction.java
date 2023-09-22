package com.example.DG.bank.system.model;

import com.example.DG.bank.system.model.enums.Method;
import com.example.DG.bank.system.model.enums.Operation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "method")
    private Method method;

    @Column(name = "operation")
    private Operation operation;


    @Column(name = "amount")
    private long amount;


    @Column(name = "date")
    private Date date;


    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private Account account;

}
