package com.example.DG.bank.system.dto;

import java.time.LocalDate;
import java.util.List;

public interface AlertProjection {
    long getId();
    String getDescription();
    LocalDate getDate();
    Long getRule();
    CustomerProjection getCustomer();
    AccountProjection getAccount();
    List<TransactionGroupProjection> getTransactionGroups();

    interface CustomerProjection {
        String getFirstName();
        String getLastName();
    }

    interface AccountProjection {
        Long getId();
    }

    interface TransactionGroupProjection {
        long getId();
        String getScope();
        Long getCustomerId();
        Long getAccountId();
        long getAggregatedAmount();
        String gettransactionType();
    }
}
