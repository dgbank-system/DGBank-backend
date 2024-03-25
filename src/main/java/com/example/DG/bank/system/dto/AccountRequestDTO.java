package com.example.DG.bank.system.dto;

import com.example.DG.bank.system.model.Customer;
import com.example.DG.bank.system.model.enums.Type;
import lombok.Data;

@Data
public class AccountRequestDTO {
    private long id;
    private Double balance;
    private Type type;
    private long customerid;
    private String customerFirstName;
    private String customerLastName;

}
