package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepo extends JpaRepository<Alert,Long> {
    List<Alert> findByCustomer_Id(Long customerId);

}
