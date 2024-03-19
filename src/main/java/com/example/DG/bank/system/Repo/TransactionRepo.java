package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    Optional<Transaction> findTransactionById(long id);

    @Query("SELECT t FROM Transaction t WHERE t.account1.customer.id = :customerId")
    List<Transaction> findAllByCustomerId(@Param("customerId") long customerId);

    List<Transaction> findAllByType(String type);

    List<Transaction> findAllByAccount1_Customer_IdAndTypeAndDate(long customerId, String type, LocalDate date);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.account1.customer.id = :customerId AND t.type = :type AND t.date = :date")
    Long sumAmountByCustomerIdAndType(@Param("customerId") long customerId, @Param("type") String type, @Param("date") LocalDate date);



}
