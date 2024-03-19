package com.example.DG.bank.system.Repo;

import com.example.DG.bank.system.model.Rule;
import com.example.DG.bank.system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface RuleRepo extends JpaRepository<Rule,Long> {


}
