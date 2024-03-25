package com.example.DG.bank.system.model;

import com.example.DG.bank.system.dto.TransactionDTO;
import com.example.DG.bank.system.model.enums.Method;
import com.example.DG.bank.system.model.enums.Operation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.lang.model.type.NullType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "type")
    private String type;


    @Column(name = "amount")
    private long amount;


    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private  String status;

    @Column(name = "description")
    private String description;

   @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "account1_id")
   @JsonIgnore
    private Account account1;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "account2_id")
    @JsonIgnore
    private Account account2;


    @Column(name = "balance")
    private Double balance;


    @ManyToMany(mappedBy = "transactions")
    private List<Alert> alerts= new ArrayList<>();




    public TransactionDTO toDTO() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(this.id);
        transactionDTO.setType(this.type);
        transactionDTO.setAmount(this.amount);
        transactionDTO.setDate(this.date);
        transactionDTO.setStatus(this.status);
        transactionDTO.setDescription(this.description);
        transactionDTO.setAlerts(this.alerts);

        if (this.account1 != null) {
            transactionDTO.setAccountId(this.account1.getId());
            transactionDTO.setBalance(this.account1.getBalance());
        }
        if (this.account2 != null) {
            transactionDTO.setAnotherAccountId(this.account2.getId());
            transactionDTO.setBalance(this.account2.getBalance());
        }
//        if(this.alert != null)
//        {
//            transactionDTO.setAlertId(this.alert.getId());
//        }

        return transactionDTO;
    }
}
