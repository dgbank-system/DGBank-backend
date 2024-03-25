package com.example.DG.bank.system.model;

import com.example.DG.bank.system.dto.AccountRequestDTO;
import com.example.DG.bank.system.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "type")
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH , CascadeType.REMOVE})
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "account")
    private List<Alert> alerts;
    public AccountRequestDTO toDTO() {
        AccountRequestDTO accountDTO = new AccountRequestDTO();
        accountDTO.setId(this.id);
        accountDTO.setBalance(this.balance);
        accountDTO.setType(this.type);

        if (this.customer != null) {

            accountDTO.setCustomerid(this.customer.getId());
            accountDTO.setCustomerFirstName(this.customer.getFirstName());
            accountDTO.setCustomerLastName(this.customer.getLastName());
        }

        return accountDTO;
    }
//   @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
//   @JsonIgnore
//   private List<Transaction> transactions;

//    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH , CascadeType.REMOVE})
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
}
