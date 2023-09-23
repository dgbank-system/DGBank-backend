package com.example.DG.bank.system.model;

import com.example.DG.bank.system.model.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    @Column(name = "balance", nullable = false)
    private long balance;

    @Column(name = "type")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY , cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH , CascadeType.REMOVE})
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH , CascadeType.REMOVE})
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
