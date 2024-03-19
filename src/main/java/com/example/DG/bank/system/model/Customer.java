package com.example.DG.bank.system.model;

import com.example.DG.bank.system.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.util.digester.Rules;

import java.util.*;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName ;

    @Column(name = "email")
    private String email;

    @Column(name="phone")
    private int phone;

    @Column(name="address")
    private String address;

    @Column(name = "date_of_birth")
    private Date date ;

    @Column(name = "gender")
    private Gender gender;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL)
    private List<Account> accounts;


    @OneToMany(mappedBy = "customer")
    private List<Alert> alerts;


}
