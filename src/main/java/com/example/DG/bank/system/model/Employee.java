package com.example.DG.bank.system.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "username", unique = true)
    private String username ;
    @Column(name = "name")
    private String name ;
    @Column(name = "address")
    private String address;
    @Column(name = " phone")
    private int phone;
    @Column(name = "email")
    private String email ;
    @Column(name = "password")
    private String password;
//    @OneToMany(mappedBy = "employee" , cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE,
//            CascadeType.DETACH,
//            CascadeType.REFRESH
//    })
//    private List<Account> accounts;
}
