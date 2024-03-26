package com.example.DG.bank.system.dto.AccountDtos;

import lombok.Data;

@Data
public class AccountRequestTRXSDto {
    private long id;
    private Double balance;
    private String customerFirstName;
    private String customerLastName;
}
