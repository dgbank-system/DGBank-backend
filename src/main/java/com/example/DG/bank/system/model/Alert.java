package com.example.DG.bank.system.model;

import com.example.DG.bank.system.dto.AlertDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "alert")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Alert {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;

    @Column(name = "description")
    private String description;


    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH , CascadeType.REMOVE})
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH , CascadeType.REMOVE})
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonIgnore
    private Account account;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "alert_transaction",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions;

    @Column(name = "belongto")
    private long rule;

    public AlertDTO toDTO() {
        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setId(this.id);
        alertDTO.setDescription(this.description);
        alertDTO.setTrxs(this.transactions);
        alertDTO.setRule(this.rule);
        alertDTO.setDate(this.date);

        if (this.customer != null) {
            alertDTO.setCustomerFirstName(this.customer.getFirstName());
            alertDTO.setCustomerLastName(this.customer.getLastName());

        }
        if(this.account != null)
        {
            alertDTO.setAccountId(this.account.getId());
        }
        return alertDTO;
    }
}
